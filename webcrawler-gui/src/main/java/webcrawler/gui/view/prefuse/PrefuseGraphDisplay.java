package webcrawler.gui.view.prefuse;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.tuple.TupleSet;
import prefuse.render.*;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import webcrawler.gui.graph.prefuse.GraphWrapper;

public class PrefuseGraphDisplay extends Display {

    public static final String COLOR_ACTION = "color";
    public static final String GRAPH_ID = "graph";
    public static final String EDGES_ID = GRAPH_ID + ".edges";
    public static final String NODES_ID = GRAPH_ID + ".nodes";
    private final Graph graph;
    private static final String LAYOUT_ACTION = "layout";
    private Logger logger = LoggerFactory.getLogger(getClass());


    public PrefuseGraphDisplay(Graph graph, GraphDisplaySettings graphDisplaySettings) {

        super(new Visualization());
        this.graph = graph;

        m_vis.addGraph(GRAPH_ID, graph);
        m_vis.setInteractive(EDGES_ID, null, false);
//        m_vis.setValue("graph.nodes", null, VisualItem.SHAPE,
//                new Integer(Constants.SHAPE_ELLIPSE));

        TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS);
        focusGroup.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
            {
                logger.debug(String.format("Tuple changed : ts=%s, add=%s, rem=%s", ts.getTupleCount(), add.length, rem.length));
            }
        });

        setupGraphRenderers();

        setupColorActions(graphDisplaySettings);

        setupLayoutAction();

        setSize(720, 500); // set display size
        pan(360, 250);
        setHighQuality(true);
        addControlListener(new DragControl());
        addControlListener(new PanControl());
        addControlListener(new ZoomControl());
    }

    private void setupGraphRenderers() {
        LabelRenderer nodeR = new LabelRenderer(GraphWrapper.NAME);
        nodeR.setRoundedCorner(8, 8);
        EdgeRenderer edgeR = new EdgeRenderer(Constants.EDGE_TYPE_CURVE, Constants.EDGE_ARROW_FORWARD);

        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeR);
        drf.setDefaultEdgeRenderer(edgeR);
        m_vis.setRendererFactory(drf);
    }

    private void setupLayoutAction() {
        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new ForceDirectedLayout(GRAPH_ID));
        layout.add(new RepaintAction());

        m_vis.putAction(LAYOUT_ACTION, layout);
        m_vis.run(LAYOUT_ACTION);
    }

    private void setupColorActions(GraphDisplaySettings graphDisplaySettings) {
        // list of colors
        int[] palette = new int[] {
                ColorLib.rgb(255, 180, 180), ColorLib.rgb(190,190,255)
        };
        DataColorAction nodeFill = new DataColorAction(NODES_ID, GraphWrapper.COLOR_FLAG,
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette);

        ColorAction nodeBorder = new ColorAction(NODES_ID, VisualItem.STROKECOLOR);
        nodeBorder.setDefaultColor(graphDisplaySettings.getNodeBorderColor());

        ColorAction edges = new ColorAction(EDGES_ID,
                VisualItem.STROKECOLOR, graphDisplaySettings.getEdgeColor());

        ColorAction textColor =  new ColorAction(NODES_ID, VisualItem.TEXTCOLOR, graphDisplaySettings.getNodeTextColor());

        ColorAction arrow = new ColorAction(EDGES_ID,
                VisualItem.FILLCOLOR, graphDisplaySettings.getNodeBackgroundColor());

        ActionList color = new ActionList();
        color.add(nodeBorder);
        color.add(nodeFill);
        color.add(edges);
        color.add(arrow);
        color.add(textColor);
        m_vis.putAction(COLOR_ACTION, color);
        m_vis.run(COLOR_ACTION);

    }

    public Graph getGraph() {
        return graph;
    }

    public void updateVisualization() {
        m_vis.setValue(NODES_ID, null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));
        m_vis.run(COLOR_ACTION);
    }

}