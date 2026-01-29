package frc.robot.WildBoard.Panels;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.WildBoard.WBPanel;

public class LooptimeMonitor extends WBPanel {
    private double lastTime_s;
    public double loopTime_ms;

    public LooptimeMonitor() {
        this.usesML = true;

        this.setPanelName("LooptimeMonitor");
    }

    @Override
    public void start() {
        lastTime_s = Timer.getTimestamp();
    }

    @Override
    public void update() {
        double curTime_s = Timer.getTimestamp();
        loopTime_ms = Math.floor((curTime_s - lastTime_s)*1000);
        lastTime_s = curTime_s;

        this.ml.send(loopTime_ms + "");
    }
}