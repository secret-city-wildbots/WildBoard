package frc.robot.WildBoard;

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
}
