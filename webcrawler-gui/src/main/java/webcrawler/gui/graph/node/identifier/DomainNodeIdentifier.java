package webcrawler.gui.graph.node.identifier;

import webcrawler.core.parsing.PageDom;
import webcrawler.core.url.UrlUtils;
import webcrawler.gui.graph.node.identifier.NodeIdentifier;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: fabricehhh
 * Date: 16.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class DomainNodeIdentifier implements NodeIdentifier {

    @Override
    public String extractNodeName(String url, PageDom pageDom) throws MalformedURLException {
        return UrlUtils.getHost(url);
    }

    @Override
    public String extractNodeKey(String url) throws MalformedURLException {
        return UrlUtils.getHost(url);
    }
}
