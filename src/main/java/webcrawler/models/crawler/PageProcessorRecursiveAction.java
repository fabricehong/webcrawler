package webcrawler.models.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.parsing.PageDom;
import webcrawler.models.tasks.PageDomTask;

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
        linkCrawlingState.newConnection(fromUrl, url);
        if (!linkCrawlingState.visited(url)) {
            try {
                PageDom pageDom = new PageDom(url);
                pageDomTask.doTask(pageDom);
                NodeList linkTags = pageDom.getTags(LinkTag.class);
                List<RecursiveAction> actions = generateNextActions(linkTags);
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

    private List<RecursiveAction> generateNextActions(NodeList list) {
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        for (int i = 0; i < list.size(); i++) {
            LinkTag extracted = (LinkTag) list.elementAt(i);

            if (!extracted.extractLink().isEmpty()
                    && !linkCrawlingState.visited(extracted.extractLink())) {

                actions.add(new PageProcessorRecursiveAction(this.pageDomTask, url, extracted.extractLink(), linkCrawlingState));
            }
        }
        return actions;
    }
}