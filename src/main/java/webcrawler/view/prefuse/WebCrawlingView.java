package webcrawler.view.prefuse;

import webcrawler.models.GraphModel;
import webcrawler.models.crawler.Webcrawler;
import webcrawler.models.listeners.GraphEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class WebCrawlingView extends JFrame implements GraphEventListener {

    private GraphModel model;
    private PrefuseGraphDisplay prefuseGraphDisplay;
    private int lalu = 0;

    public WebCrawlingView(GraphModel model, final Webcrawler webcrawler) throws HeadlessException {

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

        contentPane.setLayout(new FlowLayout());


        prefuseGraphDisplay = new PrefuseGraphDisplay(model.getGraph());
        contentPane.add(prefuseGraphDisplay);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    @Override
    public void onNodeAdded() {
        prefuseGraphDisplay.updateVisualization();
    }
}
