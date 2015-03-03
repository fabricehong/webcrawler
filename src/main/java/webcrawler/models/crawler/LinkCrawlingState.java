package webcrawler.models.crawler;

import webcrawler.models.listeners.NewLinkListener;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Madalin Ilie
 */
public class LinkCrawlingState {


    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    private List<NewLinkListener> newLinkListeners;

    public LinkCrawlingState() {
        this.newLinkListeners = new ArrayList<NewLinkListener>();
    }

    public void queueLink(String link) throws Exception {
        System.out.println("yo");
    }

    public int size() {
        return visitedLinks.size();
    }

    public void addVisited(String toLink) {
        System.out.println("visited : " + toLink);
        visitedLinks.add(toLink);

    }

    public void newConnection(String fromLink, String toLink) {
        System.out.println("connection : " + fromLink + " - "  + toLink);
        for (NewLinkListener listener : newLinkListeners) {
            listener.onNewLink(fromLink, toLink);
        }
    }

    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.newLinkListeners.add(listener);
    }

}