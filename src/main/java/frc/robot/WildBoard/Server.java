package frc.robot.WildBoard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server {

    private final Path publicDir;
    public WsServer ws;
    private HttpServer httpServer;

    /*
     * A simple http + ws implementation made by ChatGPT
     */
    public Server(int port) {
        try {
            File tmp;
            if (RobotBase.isSimulation()) {
                tmp = new File(Filesystem.getOperatingDirectory(), "sim/tmp");
            } else {
                tmp = new File("/tmp");
            }

            publicDir = Filesystem.getDeployDirectory().toPath().resolve("WildBoard/frontend/public");
            Path dynamicDir = new File(tmp, "frontend-public").toPath();

            // --- HTTP Server ---
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/dynamic/", new StaticFileHandler(dynamicDir));
            httpServer.createContext("/", new StaticFileHandler(publicDir));
            httpServer.setExecutor(null);

            // --- WebSocket Server ---
            ws = new WsServer(port + 1);
            ws.on("ping", (socket, data) -> {
                ws.emit(socket, "pong", "");
            });

            System.out.println("HTTP + WebSocket server running on port " + (port + 1));
            System.out.println("Serving files from: " + publicDir.toAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        httpServer.start();
        ws.start();
    }

    // --------------------------
    // Static File Handler
    // --------------------------
    static class StaticFileHandler implements HttpHandler {
        private final Path rootPath;
        private final Path tmpIndexJs;

        public StaticFileHandler(Path rootDir) {
            this.rootPath = rootDir.toAbsolutePath();
            this.tmpIndexJs = Path.of("/tmp/frontend-public/index.js").toAbsolutePath();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Ignore WebSocket upgrade requests
            if ("websocket".equalsIgnoreCase(exchange.getRequestHeaders().getFirst("Upgrade"))) {
                exchange.sendResponseHeaders(400, 0);
                exchange.close();
                return;
            }

            String requestPath = exchange.getRequestURI().getPath();
            if (requestPath.equals("/"))
                requestPath = "/index.html";

            Path filePath;
            if (requestPath.equals("/index.js")) {
                filePath = tmpIndexJs;
            } else {
                filePath = rootPath.resolve("." + requestPath).normalize();
                if (!filePath.startsWith(rootPath)) {
                    sendResponse(exchange, 403, "Forbidden");
                    return;
                }
            }

            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                // get the mime type
                String mimeType = URLConnection.guessContentTypeFromName(filePath.toString());

                // if it doesn't have mime type set to default of octet-stream
                if (mimeType == null)
                    mimeType = "application/octet-stream";

                // read file and send response
                byte[] fileBytes = Files.readAllBytes(filePath);
                exchange.getResponseHeaders().set("Content-Type", mimeType);
                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            } else {
                sendResponse(exchange, 404, "404 Not Found");
            }
        }

        private void sendResponse(HttpExchange exchange, int code, String message) throws IOException {
            byte[] bytes = message.getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(code, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // -----------------------
    // WebSocket Server
    // -----------------------
    static class WsServer extends WebSocketServer {
        private final Set<WebSocket> clients = Collections.synchronizedSet(new HashSet<>());
        private final ObjectMapper mapper = new ObjectMapper();
        private final Map<String, List<BiConsumer<WebSocket, Object>>> eventHandlers = new ConcurrentHashMap<>();

        public WsServer(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            clients.add(conn);
            System.out.println("Client connected: " + conn.getRemoteSocketAddress());
            emit(conn, "connected", "Welcome!");
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            clients.remove(conn);
            System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            try {
                ObjectNode msg = (ObjectNode) mapper.readTree(message);
                String event = msg.has("event") ? msg.get("event").asText() : null;
                var data = msg.has("data") ? msg.get("data") : null;

                if (event != null) {
                    trigger(event, conn, data);
                } else {
                    System.out.println("Invalid message format: " + message);
                }
            } catch (Exception e) {
                System.err.println("Invalid JSON message: " + message);
            }
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("WebSocket server ready");
        }

        // ------------------------------------
        // Event Handling
        // -------------------------------------
        public void on(String event, BiConsumer<WebSocket, Object> handler) {
            eventHandlers.computeIfAbsent(event, k -> new ArrayList<>()).add(handler);
        }

        private void trigger(String event, WebSocket conn, Object data) {
            List<BiConsumer<WebSocket, Object>> handlers = eventHandlers.get(event);
            if (handlers != null) {
                for (BiConsumer<WebSocket, Object> handler : handlers) {
                    handler.accept(conn, data);
                }
            } else {
                emit(conn, "error", "Unknown event: " + event);
            }
        }

        // -----------------------
        // Emit + Broadcast
        // -----------------------
        public void emit(WebSocket conn, String event, Object data) {
            try {
                ObjectNode msg = mapper.createObjectNode();
                msg.put("event", event);
                msg.set("data", mapper.valueToTree(data));
                conn.send(mapper.writeValueAsString(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void broadcast(String event, Object data) {
            try {
                ObjectNode msg = mapper.createObjectNode();
                msg.put("event", event);
                msg.set("data", mapper.valueToTree(data));
                String text = mapper.writeValueAsString(msg);
                synchronized (clients) {
                    for (WebSocket client : clients) {
                        client.send(text);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}