package frc.robot.WildBoard;

import frc.robot.WildBoard.Panels.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        serverStart();
        clientBuild();
        this.server.start();
    }

    private void clientBuild() {
        File deployDir = Filesystem.getDeployDirectory();
        File frontendDir = new File(deployDir, "WildBoard/frontend");

        File indexStub = new File(frontendDir, "src/pages/indexStub.tsx");
        File indexFinalDir = new File("/home/lvuser/WildBoard/frontend/src/pages");
        File indexFinal = new File("/home/lvuser/WildBoard/frontend/src/pages/index.tsx");

        indexStub.setReadable(true);
        indexFinal.setReadable(true);
        indexFinal.setWritable(true);

        try {
            // fill in all the blanks for tabs and sidepanels
            String index = Files.readString(indexStub.toPath());
            String tabs = "";
            String sidepanels = "";
            String imports = "";

            for (Tab tab: this.tabs) {
                tabs = tabs + tab.generate();
                imports+= tab.genImport() + "\n";
            }
            for (WBPanel panel: this.panels) {
                sidepanels = sidepanels + "<div class=\"column-item\" style=\"padding-bottom: 0;\">\r\n" + panel.generate() + "\n</div>";
                imports+= panel.genImport() + "\n";
            }

            index = index.replace("[TABS]", tabs)
                .replace("[SIDEPANELS]", sidepanels)
                .replace("[IMPORTS]", imports);

            indexFinalDir.mkdirs();
            Files.writeString(indexFinal.toPath(), index);
        } catch(IOException err) {
            System.out.println("couldn't read/write file in frontend construction process.");
            System.err.println(err);
        }

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