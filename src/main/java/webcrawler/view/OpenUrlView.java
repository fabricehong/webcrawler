package webcrawler.view;

import webcrawler.models.GraphModel;
import webcrawler.models.WebSiteModelImpl;
import webcrawler.models.crawler.Webcrawler;
import webcrawler.models.exceptions.TechnicalException;
import webcrawler.models.tasks.LearnTextsTask;
import webcrawler.models.tasks.PageDomTask;
import webcrawler.view.prefuse.WebCrawlingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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
        PageDomTask learnTextsTask = null;
        try {
            learnTextsTask = new LearnTextsTask("/home/fabrice/log.log");
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
        webcrawler = new Webcrawler(learnTextsTask, textField.getText(), 4);
        GraphModel graphModel = new WebSiteModelImpl(webcrawler);
        new WebCrawlingView(graphModel, webcrawler);




        webcrawler.startCrawling();
    }

}
