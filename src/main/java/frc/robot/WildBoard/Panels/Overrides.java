package frc.robot.WildBoard.Panels;

import frc.robot.WildBoard.WBPanel;

public class Overrides extends WBPanel {
    public Overrides(String[] switches, int cols) {
        this.usesML = true;

        this.setPanelName("Overrides");
        
        String blah = "";
        for (String s : switches) {
            blah+="{ label: \""+s+"\"},";
        }
        this.addRawProp("switches", "[" + blah.substring(0, blah.length() - 1) + "]");
        this.addProp("columns", cols);
    }
}
