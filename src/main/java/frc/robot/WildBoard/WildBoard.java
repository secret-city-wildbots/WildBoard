package frc.robot.WildBoard;

public class WildBoard {
    private Server server;
    private int PORT = 80;

    public WildBoard() {
        start();
    }

    public WildBoard(int PORT) {
        this.PORT = PORT;
        start();
    }

    private void start() {
        FrontendBuilder.buildFrontend();
        server = new Server(PORT);
    }
}