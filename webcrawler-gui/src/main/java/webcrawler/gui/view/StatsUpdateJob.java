package webcrawler.gui.view;

import webcrawler.core.crawler.LinkCrawlingState;
import webcrawler.core.crawler.WebCrawlingMainTask;

import javax.swing.*;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/5/15
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsUpdateJob  extends TimerTask {
    public static final String NUMBER_OF_LINKS_STR = "Number of links: %s";
    public static final String WORKING_THREADS_STR = "Working threads: %s";
    public static final String VISIT_REDUNDANCY_STR = "Visit efficiency: %s%%";
    public static final String NEW_LINKS_STR = "New links: %s";
    public static final String NEW_LINKS_ALREADY_VISITED_STR = "New links already visited: %s";

    private LinkCrawlingState linkCrawlingState;
    private WebCrawlingMainTask webCrawlingMainTask;

    private JLabel linkNumberLabel;
    private JLabel threadsWorkingLabel;
    private JLabel visitEfficiencyLabel;
    private JLabel newLinksLabel;
    private JLabel newLinksAlreadyVisitedLabel;

    private int lastLinkCollectionSize;
    private long lastLinkAlreadyVisited;

    public StatsUpdateJob(LinkCrawlingState linkCrawlingState, WebCrawlingMainTask webCrawlingMainTask, JLabel linkNumberLabel, JLabel threadsWorkingLabel, JLabel visitEfficiencyLabel, JLabel newLinksLabel, JLabel newLinksAlreadyVisitedLabel) {
        this.linkCrawlingState = linkCrawlingState;
        this.webCrawlingMainTask = webCrawlingMainTask;
        this.linkNumberLabel = linkNumberLabel;
        this.threadsWorkingLabel = threadsWorkingLabel;
        this.visitEfficiencyLabel = visitEfficiencyLabel;
        this.newLinksLabel = newLinksLabel;
        this.newLinksAlreadyVisitedLabel = newLinksAlreadyVisitedLabel;
        this.lastLinkCollectionSize = 0;
    }

    @Override
    public void run() {
        int newLinkCollectionSize = this.linkCrawlingState.getLinkCollectionSize();
        long newLinkAlreadyVisited = this.linkCrawlingState.getLinkAlreadyVisited();

        int linkCollectionSizeDelta = newLinkCollectionSize - lastLinkCollectionSize;
        long linkAlreadyVisitedDelta = newLinkAlreadyVisited - lastLinkAlreadyVisited;
        float visitEfficiency = linkAlreadyVisitedDelta!=0?(linkCollectionSizeDelta / linkAlreadyVisitedDelta)*100:0;

        updateLinkNumber(newLinkCollectionSize);
        updateNumberOfThreadsWorking(this.webCrawlingMainTask.getWorkingThreads());
        updateNewLinks(linkCollectionSizeDelta);
        updateAlreadyVisitedLinks(linkAlreadyVisitedDelta);
        updateVisitEfficiency(visitEfficiency);

        this.lastLinkCollectionSize = newLinkCollectionSize;
        this.lastLinkAlreadyVisited = newLinkAlreadyVisited;
    }

    private void updateAlreadyVisitedLinks(long alreadyVisitedLinks) {
        String text = String.format(NEW_LINKS_ALREADY_VISITED_STR, alreadyVisitedLinks);
        this.newLinksLabel.setText(text);
    }

    private void updateNewLinks(int newLinks) {
        String text = String.format(NEW_LINKS_STR, newLinks);
        this.newLinksLabel.setText(text);
    }

    private void updateNumberOfThreadsWorking(int workingThreads) {
        String text = String.format(WORKING_THREADS_STR, workingThreads);
        this.threadsWorkingLabel.setText(text);
    }

    private void updateLinkNumber(int number) {
        String text = String.format(NUMBER_OF_LINKS_STR, number);
        this.linkNumberLabel.setText(text);
    }

    private void updateVisitEfficiency(float redundancy) {
        String text = String.format(VISIT_REDUNDANCY_STR, redundancy);
        this.visitEfficiencyLabel.setText(text);
    }


}
