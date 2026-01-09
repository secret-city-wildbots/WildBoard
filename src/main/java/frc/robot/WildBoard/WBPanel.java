package frc.robot.WildBoard;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class WBPanel {
    public String panelName = "Placeholder";
    public String children = "";
    public Dictionary<String, String> props = new Hashtable<>();

    public void setProps(Dictionary<String, String> propsDic) {
        this.props = propsDic;
    }

    public void addProp(String key, String val) {
        this.props.put(key, val);
    }

    public void update() {
        //This is where any data that needs to be sent to client should be sent
    }

    /**
     * This method should take all of the inputs given and output the tsx component code needed for rendering.
     * The example given here is based on parameters, but can be changed if needed
     * @return
     */
    public String generate() {
        String propsString = "";

        Enumeration<String> e = props.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            propsString = propsString + " " + key + "={\"" + props.get(key) + "\"}";
        }
        
        return String.format("<%s %s>%s</%s>", panelName, propsString, children, panelName);
    }
}