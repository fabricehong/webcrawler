package webcrawler.models.graph.node;

import webcrawler.models.parsing.PageDom;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NodeIdentifier {
    String extractNodeName(String url, PageDom pageDom) throws MalformedURLException;

    String extractNodeKey(String url) throws MalformedURLException;
}
