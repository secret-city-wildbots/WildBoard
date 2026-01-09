package frc.robot.WildBoard;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class WBPanel {
    private String panelName = "Placeholder";
    public String children = "";
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

    /*
     * set the tsx panel name
     */
    public void setPanelName(String name) {
        this.panelName = name;
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
        
        return String.format("<%s %s>%s</%s>", panelName, propsString, children, panelName);
    }

    /*
     * Method that outputs the import statement
     */
    public String genImport() {
        return "import LooptimeMonitor from \"../panels/"+ panelName +".tsx\";";
    }
}