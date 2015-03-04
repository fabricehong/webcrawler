package webcrawler.view;

import webcrawler.models.graph.GraphModel;
import webcrawler.models.crawler.Webcrawler;
import webcrawler.models.exceptions.TechnicalException;
import webcrawler.models.graph.node.DomainNodeIdentifier;
import webcrawler.models.graph.node.NodeColorizer;
import webcrawler.models.graph.node.NodeIdentifier;
import webcrawler.models.graph.node.PageNodeIdentifier;
import webcrawler.models.tasks.LearnTextsTask;
import webcrawler.models.tasks.PageDomTask;
import webcrawler.view.prefuse.WebCrawlingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class OpenUrlView extends JFrame {

    private JButton goButton;
    private JTextField textField;
    private Webcrawler webcrawler = null;


    public OpenUrlView() throws HeadlessException {
        initGUI();
        initListeners();
    }

    private void initGUI() {
        Container contentPane = getContentPane();

        contentPane.setLayout(new FlowLayout());
        textField = new JTextField("http://www.javaworld.com");
        contentPane.add(textField);

        goButton = new JButton("Go");
        contentPane.add(goButton);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void initListeners() {
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startCrawling();
                                    }
                                }).start();

                            }
                        });

            }
        });



    }

    private void startCrawling() {
        List<PageDomTask> tasks;
        try {
            tasks = Arrays.asList(new PageDomTask[]{new LearnTextsTask("/home/fabrice/log.log")});
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
        webcrawler = new Webcrawler(tasks, textField.getText(), 4);
//        NodeIdentifier nodeIdentifier = new DomainNodeIdentifier();
        NodeIdentifier nodeIdentifier = new PageNodeIdentifier();
        NodeColorizer nodeColorizer = new NodeColorizer();
        GraphModel graphModel = new GraphModel(webcrawler, nodeColorizer, nodeIdentifier);
        new WebCrawlingView(graphModel, webcrawler);

        webcrawler.startCrawling();
    }

}
