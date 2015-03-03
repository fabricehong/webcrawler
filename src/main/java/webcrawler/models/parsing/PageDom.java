package webcrawler.models.parsing;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

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

    private Parser parser;
    public PageDom(String url) throws IOException, ParserException {
        URL uriLink = new URL(url);
        this.parser =  new Parser(uriLink.openConnection());
    }


    public NodeList getTags(Class<?> tagType) throws ParserException {
        return this.parser.extractAllNodesThatMatch(new NodeClassFilter(tagType));
    }
}
