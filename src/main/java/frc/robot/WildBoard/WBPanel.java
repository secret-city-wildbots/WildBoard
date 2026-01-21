package frc.robot.WildBoard;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class WBPanel {
    private String panelName = "Placeholder";
    public ArrayList<WBPanel> children = new ArrayList<>();
    private Dictionary<String, String> props = new Hashtable<>();
    private Dictionary<String, Integer> intprops = new Hashtable<>();
    public boolean usesML = false;
    public int id;
    public MessageLayer ml;

    public void setProps(Dictionary<String, String> propsDic) {
        this.props = propsDic;
    }

    /*
     * add a property to the tsx panel
     */
    public void addProp(String key, String val) {
        this.props.put(key, val);
    }
    public void addProp(String key, int val) {
        this.intprops.put(key, val);
    }

    public void appendToProp(String key, String val) {
        if (this.props.get(key) == null) {
            this.props.put(key, val);
        } else {
            this.props.put(key, this.props.get(key) + val);
        }
    }

    public String getProp(String key) {
        return this.props.get(key);
    }

    /*
     * set the tsx panel name
     */
    public void setPanelName(String name) {
        this.panelName = name;
    }

    public String getPanelName() {
        return this.panelName;
    }

    /*
     * Put code that should run on WB startup here
     */
    public void start() {
    }

    /*
     * This is where any data that needs to be sent to client should be sent
     */
    public void update() {
    }

    public void assignML(MessageLayer ml, int id) {
        this.ml = ml;
        this.id = id;
    }

    /**
     * This method should take all of the inputs given and output the tsx component code needed for rendering.
     * The example given here is based on parameters, but can be changed if needed
     * @return the html as a String
     */
    public String generate() {

        String propsString = "";

        if (this.usesML) {
            this.addProp("id", id);
            propsString = "socket={socket} ";
        }

        //add string props
        Enumeration<String> e = props.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            propsString = propsString + key + "={\"" + props.get(key) + "\"} ";
        }

        //add int props
        e = intprops.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            propsString = propsString + key + "={" + intprops.get(key) + "} ";
        }

        String childrenString = "";
        for (WBPanel panel: this.children) {
            childrenString = childrenString + panel.generate();
        }
        
        return String.format("<%s %s>%s</%s>", panelName, propsString, childrenString, panelName);
    }

    /*
     * Method that outputs the import statement
     */
    public String genImport() {
        return "import " + panelName + " from \"/home/lvuser/deploy/WildBoard/frontend/src/panels/"+ panelName +".tsx\";";
    }
}