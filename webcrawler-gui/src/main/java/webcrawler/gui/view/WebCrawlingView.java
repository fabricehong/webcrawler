package webcrawler.gui.view;

import webcrawler.gui.graph.GraphModel;
import webcrawler.core.crawler.Webcrawler;
import webcrawler.gui.graph.listeners.GraphEventListener;
import webcrawler.gui.view.prefuse.PrefuseGraphDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class WebCrawlingView extends JFrame implements GraphEventListener {

    public static final int STATS_INTERVAL = 1000;
    private GraphModel model;
    private PrefuseGraphDisplay prefuseGraphDisplay;
    private Webcrawler webcrawler;

    public WebCrawlingView(GraphModel model, final Webcrawler webcrawler) throws HeadlessException {
        this.webcrawler = webcrawler;
        this.model = model;
        this.model.addGraphEventListener(this);

        initGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (webcrawler!=null) {
                    webcrawler.stopCrawling();
                }
            }
        });

    }

    private void initGUI() {

        Container contentPane = getContentPane();
        this.setPreferredSize(new Dimension(1000, 500));

        contentPane.setLayout(new FlowLayout());


        prefuseGraphDisplay = new PrefuseGraphDisplay(model.getGraph());
        contentPane.add(prefuseGraphDisplay);
        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        JLabel linkNumberLabel = new JLabel();
        JLabel threadsWorkingLabel = new JLabel();
        JLabel newLinksLabel = new JLabel();
        JLabel newLinksAlreadyVisitedLabel = new JLabel();
        JLabel visitRedundancyLabel = new JLabel();

        stats.add(linkNumberLabel);
        stats.add(threadsWorkingLabel);
        stats.add(newLinksLabel);
        stats.add(newLinksAlreadyVisitedLabel);
        stats.add(visitRedundancyLabel);

        contentPane.add(stats);

        StatsUpdateJob statsUpdateJob = new StatsUpdateJob(
                this.webcrawler.getLinkCrawlingState(),
                this.webcrawler.getWebCrawlingMainTask(),
                linkNumberLabel,
                threadsWorkingLabel,
                visitRedundancyLabel,
                newLinksLabel,
                newLinksAlreadyVisitedLabel
        );
        startUpdateStatsTask(statsUpdateJob, STATS_INTERVAL);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void startUpdateStatsTask(StatsUpdateJob statsUpdateJob, int interval) {

        Timer timer = new Timer();
        timer.schedule(statsUpdateJob, 0, interval);
    }

    @Override
    public void onNodeAdded() {
        prefuseGraphDisplay.updateVisualization();
    }
}
