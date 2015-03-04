package webcrawler.gui.graph.prefuse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import webcrawler.core.exceptions.TechnicalException;
import webcrawler.gui.graph.node.NodeDescription;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.03.13
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class GraphWrapper {

    public static final String NAME = "name";
    private Graph graph;
    private Map<String, Node> strToId;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public GraphWrapper() {
        this.strToId = new HashMap<>();
        this.graph = makeGraph();
//        createNode("http://www.javaworld.co", "http://www.javaworld.com");
    }

    public boolean connectAndCreateIfNeeded(String originNodeKey, NodeDescription newNodeDescription) {
        Node from = getNode(originNodeKey);
        Node to = getNode(newNodeDescription.getKey());

        checkNotNull(from, String.format("Origin node for new node key '%s' should already exist. Origin node key : %s", newNodeDescription.getKey(), originNodeKey));

        if (to==null) {
            to = createNode(newNodeDescription);
        }

        if (!alreadyConnected(from, to)) {
            connect(from, to);
            logger.debug("connects '"+originNodeKey+"' to '"+newNodeDescription.getKey()+"'");
            return true;
        }
        logger.debug("'" + originNodeKey + "' and '" + newNodeDescription.getKey() + "' are already connected");
        return false;
    }

    private Graph makeGraph() {

        // Create tables for node and edge data, and configure their columns.
        Table nodeData = new Table();
        Table edgeData = new Table(0,1);
        nodeData.addColumn("flag", boolean.class);
        nodeData.addColumn(NAME, String.class);
        edgeData.addColumn(Graph.DEFAULT_SOURCE_KEY, int.class);
        edgeData.addColumn(Graph.DEFAULT_TARGET_KEY, int.class);
        edgeData.addColumn("label", String.class);
        // Need more data in your nodes or edges?  Just add more
        // columns.

        // Create Graph backed by those tables.  Note that I'm
        // creating a directed graph here also.
        Graph g = new Graph(nodeData, edgeData, true);
        return g;
    }

    public void truc() {
        // Create some nodes and edges, each carrying some data.
        // There are surely prettier ways to do this, but for the
        // example it gets the job done.
        for ( int i=0; i<3; ++i ) {
            Node n1 = graph.addNode();
            Node n2 = graph.addNode();
            Node n3 = graph.addNode();
            n1.setBoolean("flag", false);
            n2.setBoolean("flag", true);
            n3.setBoolean("flag", true);
            n1.setString(NAME, "asdasd1");
            n2.setString(NAME, "2");
            n3.setString(NAME, "3");
            Edge e1 = graph.addEdge(n1, n2);
            Edge e2 = graph.addEdge(n1, n3);
            Edge e3 = graph.addEdge(n2, n3);
            e1.setString("label", "a");
            e2.setString("label", "a");
            e3.setString("label", "a");
        }
        Edge e4 = graph.getEdge(graph.addEdge(0, 3));
        Edge e5 = graph.getEdge(graph.addEdge(3, 6));
        Edge e6 = graph.getEdge(graph.addEdge(6, 0));
        e4.setString("label", "b");
        e5.setString("label", "b");
        e6.setString("label", "b");
    }

    public Graph getGraph() {
        return graph;
    }

    private boolean alreadyConnected(Node key1, Node key2) {
        try {
            Edge edge = graph.getEdge(key1, key2);
            return edge!=null;
        } catch (Throwable t) {
            return false;
        }
    }

    private void connect(Node node1, Node node2) {
        Edge edge = graph.addEdge(node1, node2);
        edge.setString("label", "a");
    }

    private Node getNode(String key) {
        Node aInt = strToId.get(key);
        return aInt;
    }

    public Node createNode(NodeDescription nodeDescription) {
        if (getNode(nodeDescription.getKey())!=null) {
            throw new TechnicalException("Cannot create node. Node already exists");
        }
        Node n = graph.addNode();
        logger.debug(String.format("Node '%s' created", nodeDescription.getKey()));

        n.setString(NAME, nodeDescription.getName());
        n.setBoolean("flag", true);
        strToId.put(nodeDescription.getKey(), n);
        return n;
    }
}
