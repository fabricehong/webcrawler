package webcrawler.gui.graph.node;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeDescription {
    private String key;
    private String name;
    private Color color;

    public NodeDescription(String key, String name, Color color) {
        this.key = key;
        this.name = name;
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
