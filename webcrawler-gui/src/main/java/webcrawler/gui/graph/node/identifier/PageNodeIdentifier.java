package webcrawler.gui.graph.node.identifier;

import webcrawler.core.parsing.PageDom;
import webcrawler.gui.graph.node.identifier.NodeIdentifier;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class PageNodeIdentifier implements NodeIdentifier {
    @Override
    public String extractNodeName(String url, PageDom pageDom) {
        String pageTitle = pageDom.getPageTitle();
        return shorten(pageTitle, 10);
    }

    private String shorten(String string, int maxLen) {
        if (string.length()<=maxLen) {
            return string;
        }
        int half = maxLen/2;
        int headStop = half-1;
        return string.substring(0, half-1) + "..." + string.substring( string.length()-half+2, string.length());
    }


    @Override
    public String extractNodeKey(String url) throws MalformedURLException {
        return url;
    }
}
