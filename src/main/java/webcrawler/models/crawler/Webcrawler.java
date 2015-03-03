package webcrawler.models.crawler;

import webcrawler.models.listeners.NewLinkListener;
import webcrawler.models.tasks.PageDomTask;

import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Madalin Ilie
 */
public class Webcrawler {


    public static final String START_URL = "start";
    private String startURl;
    private ForkJoinPool mainPool;
    private LinkCrawlingState linkCrawlingState;
    private PageDomTask pageDomTask;


    public Webcrawler(PageDomTask pageDomTask, String startUrl, int maxThreads) {
        this.pageDomTask = pageDomTask;
        this.startURl = startUrl;
        mainPool = new ForkJoinPool(maxThreads);
        linkCrawlingState = new LinkCrawlingState();
    }

    public void startCrawling() {
        PageProcessorRecursiveAction start = new PageProcessorRecursiveAction(pageDomTask, START_URL, this.startURl, linkCrawlingState);
        mainPool.invoke(start);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.linkCrawlingState.addNewLinkListener(listener);
    }

    public void stopCrawling() {
        System.out.println("shutting down");
        mainPool.shutdownNow();

    }
}