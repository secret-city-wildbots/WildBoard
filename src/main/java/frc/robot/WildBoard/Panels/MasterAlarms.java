package frc.robot.WildBoard.Panels;

import frc.robot.WildBoard.WBPanel;

public class MasterAlarms extends WBPanel {
    private boolean[] oldAlarms;
    private int repeats = 0;
    private int repeatInterval = 25 + (int) Math.round(Math.random() * 10); // add some randomization to prevent offset panels
    public boolean[] alarms;

    public MasterAlarms(String[] labels, int cols) {
        this.usesML = true;

        this.alarms = new boolean[labels.length];
        for (int i = 0; i < this.alarms.length; i++) {
            this.alarms[i] = false;
        }
        this.oldAlarms = this.alarms.clone();

        this.setPanelName("MasterAlarms");
        this.addRawProp("texts", "[\"" + String.join("\",\"", labels) + "\"]");
        this.addProp("cols", cols);
    }

    public MasterAlarms(String[] labels, String[] desc, int cols) {
        this.usesML = true;

        this.alarms = new boolean[labels.length];
        for (int i = 0; i < this.alarms.length; i++) {
            this.alarms[i] = false;
        }
        this.oldAlarms = this.alarms.clone();

        this.setPanelName("MasterAlarms");
        this.addRawProp("texts", "[\"" + String.join("\",\"", labels) + "\"]");
        this.addRawProp("descriptions", "[\"" + String.join("\",\"", desc) + "\"]");
        this.addProp("cols", cols);
    }

    public void triggerAlarm(int index) {
        this.alarms[index] = true;
    }

    public void clearAlarm(int index) {
        this.alarms[index] = false;
    }

    public void setAlarm(int index, boolean value) {
        this.alarms[index] = value;
    }

    private static String boolArrayToBinaryString(boolean[] arr) {
        StringBuilder sb = new StringBuilder(arr.length);

        for (boolean b : arr) {
            sb.append(b ? '1' : '0');
        }

        return sb.toString();
    }

    @Override
    public void update() {
        if (java.util.Arrays.equals(this.oldAlarms, this.alarms) && repeats < repeatInterval) {
            repeats++;
            return;
        }
        this.ml.send(boolArrayToBinaryString(this.alarms));
        oldAlarms = this.alarms.clone();
        repeats = 0;
    }
}
