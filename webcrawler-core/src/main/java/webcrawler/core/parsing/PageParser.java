package webcrawler.core.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import webcrawler.core.exceptions.CannotVisitPageException;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/5/15
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageParser {
    public PageDom parsePage(String url) throws CannotVisitPageException {
        Document doc;
        try {
            URL uriLink = new URL(url);
            doc = Jsoup.parse(uriLink, 5000);

        } catch (Throwable e) {
            throw new CannotVisitPageException(String.format("Cannot visit url '%s'", url), e);
        }
        PageDom pageDom = new PageDom(doc);
        return pageDom;
    }
}
