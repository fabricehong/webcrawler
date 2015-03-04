package webcrawler.core.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.core.parsing.PageDom;
import webcrawler.core.tasks.PageDomTask;
import webcrawler.core.url.UrlUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * @author Madalin Ilie
 */
public class VisitUrlTask implements Callable<Void> {

    private Logger logger = LoggerFactory.getLogger(VisitUrlTask.class);
    private String urlToExplore;
    private LinkCrawlingState linkCrawlingState;
    private String fromUrl;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();
    private List<PageDomTask> pageDomTasks;
    private WebCrawlingMainTask executorService;

    public VisitUrlTask(WebCrawlingMainTask webCrawlingMainTask, List<PageDomTask> pageDomTasks, String fromUrl, String urlToExplore, LinkCrawlingState linkCrawlingState) {
        this.fromUrl = fromUrl;
        this.urlToExplore = urlToExplore;
        this.linkCrawlingState = linkCrawlingState;
        this.pageDomTasks = pageDomTasks;
        this.executorService = webCrawlingMainTask;
    }

    @Override
    public Void call() throws Exception {
        this.logger.debug(String.format("Thread '%s' visiting url '%s'. from url : %s", this, urlToExplore, fromUrl));
        String normalizedUrl = null;
        try {
            normalizedUrl = computeUrl(fromUrl, this.urlToExplore);
        } catch (MalformedURLException e) {
            this.logger.debug(String.format("Not an url (ignoring): %s", this.urlToExplore));
            return null;
        }
        if (!linkCrawlingState.isAlreadyVisited(normalizedUrl)) {
            try {
                PageDom pageDom = new PageDom(normalizedUrl);

                linkCrawlingState.addVisited(fromUrl, normalizedUrl, pageDom);

                String redirection = pageDom.getRedirection();
                if (redirection!=null) {
                    this.logger.debug(String.format("Found redirection : %s", redirection));
                    String normalizedRedirectionUrl = computeUrl(fromUrl, redirection);
                    VisitUrlTask visitUrlTask = generateNextAction(fromUrl, normalizedRedirectionUrl);
                    this.executorService.submitTask(visitUrlTask);
                    return null;
                }

                for (PageDomTask task : this.pageDomTasks) {
                    task.doTask(pageDom);
                }
                Elements links = pageDom.getLinks();

                this.logger.debug(String.format("Found %s links in this page", links.size()));
                final List<VisitUrlTask> actions = generateNextActions(normalizedUrl, links);

                if (linkCrawlingState.size() > 1500) {
                    this.logger.info("Time for visit 1500 distinct links= " + (System.nanoTime() - t0));
                    this.executorService.shutdown();
                }
                //invoke recursively
                executorService.submitAllTasks(actions);
                // invokeRecursively(actions); crée un graph bizarre

            } catch (Exception e) {
                logger.debug(String.format("error while visiting url '%s'", normalizedUrl), e);
                return null;
            }
        }
        return null;
    }

    private String computeUrl(String fromUrl, String urlToExplore) throws MalformedURLException {
        if (fromUrl==null) { // startUrl
            return urlToExplore;
        }
        if (urlToExplore.startsWith("javascript:")) {
            throw new MalformedURLException(String.format("Not an url : %s", urlToExplore));
        }
        if (urlToExplore.startsWith("mailto:")) {
            throw new MalformedURLException(String.format("Not an url : %s", urlToExplore));
        }
        if (urlToExplore.contains("://")) {
            return urlToExplore;
        }
        String toAppend = urlToExplore.startsWith("/")?urlToExplore:"/"+urlToExplore;
        String newUrl = UrlUtils.getHostUrl(fromUrl) + toAppend;
        logger.debug(String.format("Url transformation : %s -> %s", urlToExplore, newUrl));
        return newUrl;
    }

    private List<VisitUrlTask> generateNextActions(String originalUrl, Elements links) {
        List<VisitUrlTask> actions = new ArrayList<>();
        for (Element element : links) {
            VisitUrlTask task = generateNextAction(originalUrl, element.attr("href"));
            if (task!=null) {
                actions.add(task);
            }
        }
        return actions;
    }

    private VisitUrlTask generateNextAction(String originalUrl, String linkUrl) {
        if (!StringUtils.isEmpty(linkUrl) && !linkCrawlingState.isAlreadyVisited(linkUrl)) {
            return new VisitUrlTask(this.executorService, this.pageDomTasks, originalUrl, linkUrl, linkCrawlingState);
        }
        return null;
    }

}