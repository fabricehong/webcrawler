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
        return pageDom.getPageTitle();
    }

    @Override
    public String extractNodeKey(String url) throws MalformedURLException {
        return url;
    }
}
