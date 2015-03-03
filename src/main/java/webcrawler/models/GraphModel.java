package webcrawler.models;

import prefuse.data.Graph;
import prefuse.data.Node;
import webcrawler.models.listeners.GraphEventListener;
import webcrawler.models.listeners.NewLinkListener;
import webcrawler.models.crawler.Webcrawler;

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
public abstract class GraphModel implements NewLinkListener {

    private Webcrawler webcrawler;
    private List<GraphEventListener> graphEventListenerList;
    private GraphWrapper graphWrapper;


    public GraphModel(Webcrawler webcrawler) {
        this.graphEventListenerList = new ArrayList<GraphEventListener>();
        //this.webSites = new HashSet<String>();
        webcrawler.addNewLinkListener(this);
        this.graphWrapper = new GraphWrapper();
    }


    @Override
    public synchronized void onNewLink(String fromLink, String toLink, String nodeName) {
        String fromKey = null;
        String toKey = null;
        try {
            fromKey = extractKeyFromLink(fromLink);
            toKey = extractKeyFromLink(toLink);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (fromKey.equalsIgnoreCase(toKey)) {
            return;
        }

        Node from;
        Node to;
        from = graphWrapper.getNode(fromKey);
        to = graphWrapper.getNode(toKey);

        if (from==null) {
            from = graphWrapper.createNode(fromKey, nodeName);
            System.out.println("node '"+fromKey+"' created");
        }
        if (to==null) {
            to = graphWrapper.createNode(toKey, nodeName);
            System.out.println("node '"+toKey+"' created");
        }

        if (!graphWrapper.alreadyConnected(from, to)) {
            graphWrapper.connect(from, to);
            notifyNodeAdded();
            System.out.println("connects '"+fromKey+"' to '"+toKey+"'");
        } else {
            System.out.println("'" + fromKey + "' and '" + toKey + "' are already connected");
        }
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

    protected abstract String extractNodeName(String key, String link);

    protected abstract String extractKeyFromLink(String url) throws MalformedURLException;


}
