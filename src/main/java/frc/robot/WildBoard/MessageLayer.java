package frc.robot.WildBoard;

public class MessageLayer {
    private Server server;
    public int elemID;

    public MessageLayer(Server server, int elemID) {
        this.server = server;
        this.elemID = elemID;
    }

    public void send(String msg) {
        this.server.ws.broadcast("e"+elemID+"."+msg);
    }
}