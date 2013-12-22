package webcrawler.models;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import webcrawler.models.exceptions.NodeDoesntExists;

import java.util.HashMap;
import java.util.Map;

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

    public GraphWrapper() {
        this.strToId = new HashMap<String, Node>();
        this.graph = makeGraph();
        createNode("start", "start");
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

    public boolean alreadyConnected(Node key1, Node key2) {
        try {
            Edge edge = graph.getEdge(key1, key2);
            return edge!=null;
        } catch (Throwable t) {
            return false;
        }
    }

    public void connect(Node node1, Node node2) {
        Edge edge = graph.addEdge(node1, node2);
        edge.setString("label", "a");

    }

    public Node getNode(String key) {
        Node aInt = strToId.get(key);
        return aInt;
    }

    public Node createNode(String key, String name) {
        Node n = graph.addNode();
        n.setString(NAME, name);
        n.setBoolean("flag", false);
        strToId.put(key, n);
        return n;
    }
}
