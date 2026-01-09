package frc.robot.WildBoard;

import frc.robot.WildBoard.Panels.*;

import java.io.File;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;

public class WildBoard {
    private Server server;
    private int PORT = 5804;
    private ArrayList<WBPanel> panels = new ArrayList<WBPanel>();
    private ArrayList<Tab> tabs = new ArrayList<Tab>();

    public WildBoard() {
    }

    public WildBoard(int PORT) {
        this.PORT = PORT;
    }

    public void addPanel(WBPanel panel) {
        panels.add(panel);
    }

    public void addTab(Tab tab) {
        tabs.add(tab);
    }

    public void start() {
        clientBuild();
        serverStart();
    }

    private void clientBuild() {
        File deployDir = Filesystem.getDeployDirectory();
        File frontendDir = new File(deployDir, "WildBoard/frontend");


        FrontendBuilder.buildFrontend();
    }

    private void serverStart() {
        server = new Server(PORT);

        //startup panels
        for (int i = 0; i < panels.size(); i++) {
            WBPanel wbPanel = panels.get(i);

            if (wbPanel.usesML) {
                System.out.println("assigned ml id " + i);
                wbPanel.assignML(new MessageLayer(server, i), i);
            }
            wbPanel.start();
        }
    }

    /*
     * Call this method within RobotPeriodic in order for the dashboard
     * to function properly
     */
    public void update() {
        //update all panels
        for (WBPanel wbPanel : panels) {
            wbPanel.update();
        }
    }
}