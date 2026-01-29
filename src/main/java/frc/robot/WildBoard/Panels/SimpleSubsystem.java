package frc.robot.WildBoard.Panels;

import frc.robot.WildBoard.WBPanel;

public class SimpleSubsystem extends WBPanel {
    public SimpleSubsystem(String name, boolean absolute) {
        this.usesML = true;

        this.setPanelName("SimpleSubsystem");
        this.addProp("name", name);
        this.addProp("absolute", absolute);
        this.addProp("velocity", false);
    }

    public void updateVals(double pos, double temp_C) {
        this.ml.send(pos + "," + temp_C);

    }
}