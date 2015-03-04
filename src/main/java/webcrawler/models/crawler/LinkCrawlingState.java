package webcrawler.models.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.listeners.NewLinkListener;
import webcrawler.models.parsing.PageDom;

import java.util.*;

/**
 *
 * @author Madalin Ilie
 */
public class LinkCrawlingState {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    private List<NewLinkListener> newLinkListeners;

    public LinkCrawlingState() {
        this.newLinkListeners = new ArrayList<NewLinkListener>();
    }

    public int size() {
        return visitedLinks.size();
    }

    public void addVisited(String fromLink, String toLink, PageDom pageDom) {
        logger.info("visited : " + toLink);
        visitedLinks.add(toLink);
        newConnection(fromLink, toLink, pageDom);
    }

    private void newConnection(String fromLink, String toLink, PageDom pageDom) {
        logger.info("Creating connection : " + fromLink + " - " + toLink);
        for (NewLinkListener listener : newLinkListeners) {
            listener.onVisitNewUrl(fromLink, toLink, pageDom);
        }
    }

    public boolean isAlreadyVisited(String s) {
        return visitedLinks.contains(s);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.newLinkListeners.add(listener);
    }

}