package webcrawler.models.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/3/15
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageDom {

    private final Document doc;

    public PageDom(String url) throws IOException {
        URL uriLink = new URL(url);
        doc = Jsoup.parse(uriLink, 5000);
    }


    public Elements getLinks() {
        Elements links = doc.select("a");
        return links;
    }

    public Elements getParagraphs() {
        Elements paragraphs = doc.select("p");
        return paragraphs;
    }

    public String getPageTitle() {
        Elements head = doc.select("head");
        if (head.size()!=1) {
            return "body?";
        }
        Element element = head.get(0);
        Elements title = element.getElementsByTag("title");
        if (title.size()!=1) {
            return "title?";
        }
        return title.get(0).text();
    }
}
