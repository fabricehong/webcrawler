package webcrawler.models.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.parsing.PageDom;
import webcrawler.models.tasks.PageDomTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Madalin Ilie
 */
public class PageProcessorRecursiveAction extends RecursiveAction {

    private Logger logger = LoggerFactory.getLogger(PageProcessorRecursiveAction.class);
    private String url;
    private LinkCrawlingState linkCrawlingState;
    private String fromUrl;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();
    private PageDomTask pageDomTask;

    public PageProcessorRecursiveAction(PageDomTask pageDomTask, String fromUrl, String urlToExplore, LinkCrawlingState linkCrawlingState) {
        this.fromUrl = fromUrl;
        this.url = urlToExplore;
        this.linkCrawlingState = linkCrawlingState;
        this.pageDomTask = pageDomTask;
    }

    @Override
    public void compute() {
        if (!linkCrawlingState.visited(url)) {
            try {
                PageDom pageDom = new PageDom(url);
                linkCrawlingState.newConnection(fromUrl, url, pageDom.getPageTitle());
                pageDomTask.doTask(pageDom);
                Elements links = pageDom.getLinks();
//                NodeList linkTags = pageDom.getTags(LinkTag.class);
                List<RecursiveAction> actions = generateNextActions(links);
                linkCrawlingState.addVisited(url);

                if (linkCrawlingState.size() == 1500) {
                    System.out.println("Time for visit 1500 distinct links= " + (System.nanoTime() - t0));
                }
                //invoke recursively
                invokeAll(actions);
            } catch (Exception e) {
                logger.error(String.format("error while visiting url '%s'", url), e);
            }
        }
    }

    private List<RecursiveAction> generateNextActions(Elements links) {
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        for (int i = 0; i < links.size(); i++) {
            Element element = links.get(i);
            String href = element.attr("href");
            if (!StringUtils.isEmpty(href) && !linkCrawlingState.visited(href)) {

                actions.add(new PageProcessorRecursiveAction(this.pageDomTask, url, href, linkCrawlingState));
            }
        }
        return actions;
    }
}