package frc.robot.WildBoard;

import frc.robot.WildBoard.Panels.*;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class WildBoard {
    public Server server;
    private int PORT = 5804;
    private double lastTime_s;
    public double loopTime_ms;
    public ArrayList<WBPanel> panels = new ArrayList<WBPanel>();

    public WildBoard() {
    }

    public WildBoard(int PORT) {
        this.PORT = PORT;
    }

    public void addPanel(WBPanel panel) {
        panels.add(panel);
    }

    public void start() {
        LooptimeMonitor looptimeMonitor = new LooptimeMonitor();
        System.out.println(looptimeMonitor.generate());

        FrontendBuilder.buildFrontend();
        server = new Server(PORT);

        lastTime_s = Timer.getTimestamp();

        server.ws.on("test", (socket, data) -> {
            System.out.println("recieved test message: "+data);
            server.ws.emit(socket, "test", "Hello from server!");
        });
    }

    public void periodic() {
        double curTime_s = Timer.getTimestamp();
        loopTime_ms = Math.floor((curTime_s - lastTime_s)*1000);
        lastTime_s = curTime_s;

        //update all panels
        for (WBPanel wbPanel : panels) {
            wbPanel.update();
        }

        //server.ws.broadcast("looptime", loopTime_ms);
    }
}