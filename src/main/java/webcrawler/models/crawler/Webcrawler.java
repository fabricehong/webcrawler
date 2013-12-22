package webcrawler.models.crawler;

import webcrawler.models.listeners.NewLinkListener;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Madalin Ilie
 */
public class Webcrawler implements LinkHandler {


    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    //    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<>());
    private String url;
    private ForkJoinPool mainPool;
    private List<NewLinkListener> newLinkListeners;

    public Webcrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
        this.newLinkListeners = new ArrayList<NewLinkListener>();


    }

    public void startCrawling() {
        mainPool.invoke(new LinkFinderAction("start", this.url, this));
    }

    @Override
    public void queueLink(String link) throws Exception {
        System.out.println("yo");
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String toLink) {
        System.out.println("visited : " + toLink);
        visitedLinks.add(toLink);

    }

    @Override
    public void newConnection(String fromLink, String toLink) {
        System.out.println("connection : " + fromLink + " - "  + toLink);
        for (NewLinkListener listener : newLinkListeners) {
            listener.onNewLink(fromLink, toLink);
        }
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.newLinkListeners.add(listener);
    }

    public void stopCrawling() {
        System.out.println("shutting down");
        mainPool.shutdownNow();

    }
}