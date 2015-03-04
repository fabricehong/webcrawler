package webcrawler.models.url;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class UrlUtils {

    public static String getHost(String link) throws MalformedURLException {
        URL url = new URL(link);

        String host = url.getHost();
        return host;
    }

    public static String getHostUrl(String link) throws MalformedURLException {
        URL url = new URL(link);
        return url.getProtocol() + "://" + url.getHost();
    }
}
