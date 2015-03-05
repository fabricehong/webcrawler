package webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.core.listeners.NewLinkListener;
import webcrawler.core.parsing.PageDom;

import java.util.*;

/**
 *
 * @author Madalin Ilie
 */
public class LinkCrawlingState {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    private List<NewLinkListener> newLinkListeners;
    private long linkAlreadyVisited;

    public LinkCrawlingState() {
        this.linkAlreadyVisited = 0;
        this.newLinkListeners = new ArrayList<NewLinkListener>();
    }

    public synchronized int getLinkCollectionSize() {
        return visitedLinks.size();
    }

    public synchronized void addVisited(String fromLink, String toLink, PageDom pageDom) {
        logger.debug("visited : " + toLink);
        visitedLinks.add(toLink);
        newConnection(fromLink, toLink, pageDom);
    }

    private void newConnection(String fromLink, String toLink, PageDom pageDom) {
        logger.debug("Creating connection : " + fromLink + " - " + toLink);
        for (NewLinkListener listener : newLinkListeners) {
            listener.onVisitNewUrl(fromLink, toLink, pageDom);
        }
    }

    public synchronized boolean isAlreadyVisited(String s) {
        boolean contains = visitedLinks.contains(s);
        if (contains) {
            linkAlreadyVisited++;
        }
        return contains;
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.newLinkListeners.add(listener);
    }

    public long getLinkAlreadyVisited() {
        return linkAlreadyVisited;
    }
}