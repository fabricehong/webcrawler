package webcrawler.models;

import webcrawler.models.crawler.Webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabricehhh
 * Date: 16.03.13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class WebSiteModelImpl extends GraphModel {
    public WebSiteModelImpl(Webcrawler webcrawler) {
        super(webcrawler);
    }

    protected String extractNodeName(String key, String link) {
        return key;
    }

    protected String extractKeyFromLink(String url) throws MalformedURLException {
        URL aUrl = null;
        try {
            aUrl = new URL(url);
        } catch (MalformedURLException e) {
            return url;
        }
        return aUrl!=null?aUrl.getHost():"NULL";
    }
}
