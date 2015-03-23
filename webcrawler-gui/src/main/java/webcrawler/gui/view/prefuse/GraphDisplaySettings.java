package webcrawler.gui.view.prefuse;

import prefuse.util.ColorLib;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/23/15
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphDisplaySettings {
    private int nodeBorderColor = ColorLib.gray(100);
    private int edgeColor = ColorLib.gray(200);
    private int nodeTextColor = ColorLib.rgb(0,0,0);
    private int nodeBackgroundColor = ColorLib.gray(200);

    public int getNodeBorderColor() {
        return nodeBorderColor;
    }

    public int getEdgeColor() {
        return edgeColor;
    }

    public int getNodeTextColor() {
        return nodeTextColor;
    }

    public int getNodeBackgroundColor() {
        return nodeBackgroundColor;
    }
}
