package webcrawler.gui.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prefuse.data.Graph;
import webcrawler.gui.graph.node.colorizer.NodeColorizer;
import webcrawler.gui.graph.node.NodeDescription;
import webcrawler.gui.graph.node.identifier.NodeIdentifier;
import webcrawler.gui.graph.listeners.GraphEventListener;
import webcrawler.core.listeners.NewLinkListener;
import webcrawler.core.crawler.Webcrawler;
import webcrawler.core.parsing.PageDom;
import webcrawler.gui.graph.prefuse.GraphWrapper;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class GraphModel implements NewLinkListener {
    private List<GraphEventListener> graphEventListenerList;
    private GraphWrapper graphWrapper;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private NodeIdentifier nodeIdentifier;
    private NodeColorizer nodeColorizer;

    public GraphModel(Webcrawler webcrawler, NodeColorizer nodeColorizer, NodeIdentifier nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
        this.nodeColorizer = nodeColorizer;
        this.graphEventListenerList = new ArrayList<>();
        //this.webSites = new HashSet<String>();
        webcrawler.addNewLinkListener(this);
        this.graphWrapper = new GraphWrapper();
    }

    @Override
    public synchronized void onVisitNewUrl(String fromUrl, String toUrl, PageDom pageDom) {
        if (fromUrl==null) { // first node
            try {
                graphWrapper.createNode(createNodeDescription(toUrl, pageDom));
            } catch (MalformedURLException e) {
                logger.debug(String.format("Error while creating the first node : %s", toUrl), e);
                return;
            }
            return;
        }

        String originNodeKey = null;
        try {
            originNodeKey = this.nodeIdentifier.extractNodeKey(fromUrl);
        } catch (MalformedURLException e) {
            logger.debug(String.format("Origin node url is malformed : %s", toUrl), e);
            return;
        }
        NodeDescription newNodeDescription;
        try {
            newNodeDescription = createNodeDescription(toUrl, pageDom);
        } catch (MalformedURLException e) {
            logger.debug(String.format("New node url is malformed : %s", toUrl), e);
            return;
        }

        if (originNodeKey.equalsIgnoreCase(newNodeDescription.getKey())) {
            this.logger.debug(String.format("Do nothing, same nodes for '%s'", originNodeKey));
            return;
        }

        boolean newConnection = this.graphWrapper.connectAndCreateIfNeeded(originNodeKey, newNodeDescription);
        if (newConnection) {
            notifyNodeAdded();
        }
    }

    private NodeDescription createNodeDescription(String url, PageDom pageDom) throws MalformedURLException {
        String nodeKey = this.nodeIdentifier.extractNodeKey(url);
        String nodeName = this.nodeIdentifier.extractNodeName(url, pageDom);
        Color nodeColor = nodeColorizer.getNodeColor(url, pageDom);
        return new NodeDescription(nodeKey, nodeName, nodeColor);
    }

    private void notifyNodeAdded() {
        for (GraphEventListener graphEvent : graphEventListenerList) {
            graphEvent.onNodeAdded();
        }
    }

    public Graph getGraph() {
        return graphWrapper.getGraph();
    }

    public void addGraphEventListener(GraphEventListener graphEventListener) {
        this.graphEventListenerList.add(graphEventListener);
    }
}
