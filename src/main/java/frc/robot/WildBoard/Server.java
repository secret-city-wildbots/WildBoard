package frc.robot.WildBoard;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {

    private final Path publicDir;

    public Server(int PORT) {
        try {
            // Use Filesystem.getDeployDirectory() to locate the deploy folder dynamically
            publicDir = Filesystem.getDeployDirectory().toPath().resolve("WildBoard/frontend/public");

            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new StaticFileHandler(publicDir));

            server.setExecutor(null);
            server.start();

            System.out.println("Server is running on port " + PORT);
            System.out.println("Serving files from: " + publicDir.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static class StaticFileHandler implements HttpHandler {
        private final Path rootPath;
        private final Path tmpIndexJs;
    
        public StaticFileHandler(Path rootDir) {
            this.rootPath = rootDir.toAbsolutePath();
            this.tmpIndexJs = Path.of("/tmp/frontend-public/index.js").toAbsolutePath();
        }
    
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
    
            // Default to index.html for root
            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }
    
            Path filePath;
    
            // If the request is /index.js, use the /tmp folder
            if (requestPath.equals("/index.js")) {
                filePath = tmpIndexJs;
            } else {
                filePath = rootPath.resolve("." + requestPath).normalize();
    
                // Prevent directory traversal
                if (!filePath.startsWith(rootPath)) {
                    sendResponse(exchange, 403, "Forbidden");
                    return;
                }
            }
    
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String mimeType = URLConnection.guessContentTypeFromName(filePath.toString());
                if (mimeType == null) mimeType = "application/octet-stream";
    
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
}