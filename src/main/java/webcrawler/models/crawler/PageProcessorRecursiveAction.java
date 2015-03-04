package webcrawler.models.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.parsing.PageDom;
import webcrawler.models.tasks.PageDomTask;
import webcrawler.models.url.UrlUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Madalin Ilie
 */
public class PageProcessorRecursiveAction extends RecursiveAction {

    private Logger logger = LoggerFactory.getLogger(PageProcessorRecursiveAction.class);
    private String urlToExplore;
    private LinkCrawlingState linkCrawlingState;
    private String fromUrl;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();
    private List<PageDomTask> pageDomTasks;

    public PageProcessorRecursiveAction(List<PageDomTask> pageDomTasks, String fromUrl, String urlToExplore, LinkCrawlingState linkCrawlingState) {
        this.fromUrl = fromUrl;
        this.urlToExplore = urlToExplore;
        this.linkCrawlingState = linkCrawlingState;
        this.pageDomTasks = pageDomTasks;
    }

    @Override
    public void compute() {
        String url = null;
        try {
            url = computeUrl(fromUrl, this.urlToExplore);
        } catch (MalformedURLException e) {
            this.logger.debug(String.format("Not an url (ignoring): %s", this.urlToExplore));
            return;
        }
        if (!linkCrawlingState.isAlreadyVisited(url)) {
            try {
                PageDom pageDom = new PageDom(url);

                linkCrawlingState.addVisited(fromUrl, url, pageDom);
                for (PageDomTask task : this.pageDomTasks) {
                    task.doTask(pageDom);
                }
                Elements links = pageDom.getLinks();
                List<RecursiveAction> actions = generateNextActions(links, url);

                if (linkCrawlingState.size() == 1500) {
                    this.logger.info("Time for visit 1500 distinct links= " + (System.nanoTime() - t0));
                }
                //invoke recursively
                invokeAll(actions);
            } catch (Exception e) {
                logger.debug(String.format("error while visiting url '%s'", url), e);
            }
        }
    }

    private String computeUrl(String fromUrl, String urlToExplore) throws MalformedURLException {
        if (fromUrl==null) { // startUrl
            return urlToExplore;
        }
        if (urlToExplore.startsWith("javascript:")) {
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

    private List<RecursiveAction> generateNextActions(Elements links, String url) {
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        for (int i = 0; i < links.size(); i++) {
            Element element = links.get(i);
            String href = element.attr("href");
            if (!StringUtils.isEmpty(href) && !linkCrawlingState.isAlreadyVisited(href)) {
                actions.add(new PageProcessorRecursiveAction(this.pageDomTasks, url, href, linkCrawlingState));
            }
        }
        return actions;
    }
}