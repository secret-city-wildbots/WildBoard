package frc.robot.WildBoard.Panels;

import frc.robot.WildBoard.WBPanel;

public class VelocitySimpleSubsystem extends WBPanel {
    public VelocitySimpleSubsystem(String name) {
        this.usesML = true;

        this.setPanelName("SimpleSubsystem");
        this.addProp("name", name);
        this.addProp("velocity", true);
    }

    public void updateVals(double pos, double temp_C) {
        this.ml.send(pos + "," + temp_C);

    }
}