package webcrawler.view;

import webcrawler.models.GraphModel;
import webcrawler.models.LinkModelImpl;
import webcrawler.models.WebSiteModelImpl;
import webcrawler.models.crawler.Webcrawler;
import webcrawler.view.prefuse.WebsiteView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class ControlFrameView extends JFrame {

    private JButton goButton;
    private JTextField textField;
    private Webcrawler webcrawler = null;


    public ControlFrameView() throws HeadlessException {


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
        webcrawler = new Webcrawler(textField.getText(), 4);
        GraphModel graphModel = new WebSiteModelImpl(webcrawler);
        new WebsiteView(graphModel, webcrawler);




        webcrawler.startCrawling();
    }

}
