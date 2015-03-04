package webcrawler.view.prefuse;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.render.*;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class PrefuseGraphDisplay extends Display {

    public static final String COLOR_ACTION = "color";
    private final Graph graph;
    private static final String LAYOUT_ACTION = "layout";



    public PrefuseGraphDisplay(Graph graph) {

        super(new Visualization());
        this.graph = graph;

        m_vis.addGraph("graph", graph);
        m_vis.setInteractive("graph.edges", null, false);
//        m_vis.setValue("graph.nodes", null, VisualItem.SHAPE,
//                new Integer(Constants.SHAPE_ELLIPSE));



        LabelRenderer nodeR = new LabelRenderer("name");
        nodeR.setRoundedCorner(8, 8);
        EdgeRenderer edgeR = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE, prefuse.Constants.EDGE_ARROW_FORWARD);

        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeR);
        drf.setDefaultEdgeRenderer(edgeR);
        m_vis.setRendererFactory(drf);

        int[] palette = new int[] {
                ColorLib.rgb(255,180,180), ColorLib.rgb(190,190,255)
        };
        ColorAction nStroke = new ColorAction("graph.nodes", VisualItem.STROKECOLOR);
        nStroke.setDefaultColor(ColorLib.gray(100));

        DataColorAction nFill = new DataColorAction("graph.nodes", "flag",
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        ColorAction edges = new ColorAction("graph.edges",
                VisualItem.STROKECOLOR, ColorLib.gray(200));

        ColorAction textColor =  new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0));

        ColorAction arrow = new ColorAction("graph.edges",
                VisualItem.FILLCOLOR, ColorLib.gray(200));
        ActionList color = new ActionList();
        color.add(nStroke);
        color.add(nFill);
        color.add(edges);
        color.add(arrow);
        color.add(textColor);

        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new ForceDirectedLayout("graph"));
        layout.add(new RepaintAction());

        m_vis.putAction(COLOR_ACTION, color);
        m_vis.putAction(LAYOUT_ACTION, layout);

        setSize(720, 500); // set display size
        pan(360, 250);
        setHighQuality(true);
        addControlListener(new DragControl());
        addControlListener(new PanControl());
        addControlListener(new ZoomControl());

        m_vis.run(COLOR_ACTION);
        m_vis.run(LAYOUT_ACTION);
    }

    public Graph getGraph() {
        return graph;
    }

    public void updateVisualization() {
        m_vis.setValue("graph.nodes", null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));
        m_vis.run(COLOR_ACTION);
    }

}