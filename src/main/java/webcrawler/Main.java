package webcrawler;

import webcrawler.view.ControlFrameView;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.03.13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        startCrawlerGui();
        //startPrefuseTest();
    }

    private static void startCrawlerGui() {
        new ControlFrameView();
    }

}
