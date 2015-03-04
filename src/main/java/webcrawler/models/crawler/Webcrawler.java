package webcrawler.models.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.listeners.NewLinkListener;
import webcrawler.models.tasks.PageDomTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Madalin Ilie
 */
public class Webcrawler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String startURl;
    private ForkJoinPool mainPool;
    private LinkCrawlingState linkCrawlingState;
    private List<PageDomTask> pageDomTasks;


    public Webcrawler(List<PageDomTask> pageDomTasks, String startUrl, int maxThreads) {
        this.pageDomTasks = pageDomTasks;
        this.startURl = startUrl;
        mainPool = new ForkJoinPool(maxThreads);
        linkCrawlingState = new LinkCrawlingState();
    }

    public void startCrawling() {
        PageProcessorRecursiveAction start = new PageProcessorRecursiveAction(this.pageDomTasks, null, this.startURl, linkCrawlingState);
        mainPool.invoke(start);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.linkCrawlingState.addNewLinkListener(listener);
    }

    public void stopCrawling() {
        logger.info("shutting down");
        mainPool.shutdownNow();

    }
}