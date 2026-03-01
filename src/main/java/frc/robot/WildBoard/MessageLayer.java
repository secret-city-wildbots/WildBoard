package frc.robot.WildBoard;

import java.util.function.Consumer;

public class MessageLayer {
    public Server server;
    public int elemID;

    public MessageLayer(Server server, int elemID) {
        this.server = server;
        this.elemID = elemID;
    }

    public void send(String msg) {
        // enqueue instead of immediate websocket send
        this.server.ws.enqueue("e" + elemID + "." + msg);
    }

    /**
     * Bind to incoming messages on this id. ONLY DO ONCE PER PANEL
     * @param handler
     */
    public void bind(Consumer<String> handler) {
        this.server.ws.bind(elemID, handler);
    }
}
