package webcrawler.core.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webcrawler.core.exceptions.CannotVisitPageException;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/3/15
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageDom {

    public static final String URL_PROP = "URL=";
    private final Document doc;

    public PageDom(Document doc) {
        this.doc = doc;
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
        Element head = getHead();
        if (head==null) {
            return "body?";
        }
        Elements title = head.getElementsByTag("title");
        if (title.size()!=1) {
            return "title?";
        }
        return title.get(0).text();
    }

    private Element getHead() {
        Elements head = doc.select("head");
        if (head.size()!=1) {
            return null;
        }

        return head.get(0);
    }

    public String getRedirection() {
        Element head = getHead();
        if (head==null) {
            return null;
        }
        Elements meta = head.getElementsByAttributeValueContaining("content", "URL=");
        if (meta.size()==0) {
            return null;
        }
        String content = meta.get(0).attr("content");
        if (content==null) {
            return null;
        }
        return getRedirectInContentAttr(content);
    }

    protected static String getRedirectInContentAttr(String content) {
        int indexStart = content.indexOf(URL_PROP) + URL_PROP.length();
        int indexEnd = Math.max(content.indexOf(";", indexStart), content.length());
        return content.substring(indexStart, indexEnd);
    }
}
