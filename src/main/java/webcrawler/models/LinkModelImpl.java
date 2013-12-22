package webcrawler.models;

import webcrawler.models.crawler.Webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabricehhh
 * Date: 16.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class LinkModelImpl extends GraphModel {
    public LinkModelImpl(Webcrawler webcrawler) {
        super(webcrawler);
    }

    @Override
    protected String extractNodeName(String key, String link) {
        URL url = null;

        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }

        String host = url.getHost();
        String file = url.getFile();
        System.out.println(host);
        return host.split("\\.")[1];
        //return host.split("\\.")[1] + " - " + file;
    }

    @Override
    protected String extractKeyFromLink(String url) throws MalformedURLException {
        return url;
    }
}
