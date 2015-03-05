package webcrawler.core.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.core.parsing.PageDom;
import webcrawler.core.parsing.PageParser;
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

    public static final int MAX_LINKS = 300;
    private Logger logger = LoggerFactory.getLogger(VisitUrlTask.class);
    private String urlToExplore;
    private LinkCrawlingState linkCrawlingState;
    private String fromUrl;
    /**
     * Used for statistics
     */
    private static final long t0 = System.currentTimeMillis();
    private List<PageDomTask> pageDomTasks;
    private WebCrawlingMainTask webCrawlingMainTask;

    private PageParser pageParser;

    public VisitUrlTask(WebCrawlingMainTask webCrawlingMainTask, List<PageDomTask> pageDomTasks, PageParser pageParser, String fromUrl, String urlToExplore, LinkCrawlingState linkCrawlingState) {
        this.fromUrl = fromUrl;
        this.urlToExplore = urlToExplore;
        this.linkCrawlingState = linkCrawlingState;
        this.pageDomTasks = pageDomTasks;
        this.pageParser = pageParser;
        this.webCrawlingMainTask = webCrawlingMainTask;
    }

    @Override
    public Void call() throws Exception {
        if (linkCrawlingState.getLinkCollectionSize() > MAX_LINKS) {
            this.logger.info(String.format("Time for visit %s distinct links=%s s", MAX_LINKS, ((System.currentTimeMillis() - t0)/1000)));
            this.webCrawlingMainTask.shutdown();
            return null;
        }

        this.logger.debug(String.format("Thread '%s' visiting url '%s'. from url : %s", this, urlToExplore, fromUrl));
        String normalizedUrl = null;
        try {
            normalizedUrl = computeUrl(fromUrl, this.urlToExplore);
        } catch (MalformedURLException e) {
            this.logger.debug(String.format("Not an url (ignoring): %s", this.urlToExplore));
            return null;
        }
        if (!isAlreadyVisited(normalizedUrl)) {
            try {
                PageDom pageDom = pageParser.parsePage(normalizedUrl);

                linkCrawlingState.addVisited(fromUrl, normalizedUrl, pageDom);

                String redirection = pageDom.getRedirection();
                if (redirection!=null) {
                    this.logger.debug(String.format("Found redirection : %s", redirection));
                    String normalizedRedirectionUrl = computeUrl(fromUrl, redirection);
                    VisitUrlTask visitUrlTask = generateNextAction(fromUrl, normalizedRedirectionUrl);
                    this.webCrawlingMainTask.submitTask(visitUrlTask);
                    return null;
                }

                for (PageDomTask task : this.pageDomTasks) {
                    task.doTask(pageDom);
                }
                Elements links = pageDom.getLinks();

                this.logger.debug(String.format("Found %s links in this page", links.size()));
                final List<VisitUrlTask> actions = generateNextActions(normalizedUrl, links);


                //invoke recursively
                webCrawlingMainTask.submitAllTasks(actions);
                // invokeRecursively(actions); crée un graph bizarre

            } catch (Exception e) {
                logger.debug(String.format("error while visiting url '%s'", normalizedUrl), e);
                return null;
            }
        }
        return null;
    }

    private boolean isAlreadyVisited(String normalizedUrl) {
        return linkCrawlingState.isAlreadyVisited(normalizedUrl);
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
        if (!StringUtils.isEmpty(linkUrl) && !isAlreadyVisited(linkUrl)) {
            return new VisitUrlTask(this.webCrawlingMainTask, this.pageDomTasks, this.pageParser, originalUrl, linkUrl, linkCrawlingState);
        }
        return null;
    }

}