package webcrawler.core.parsing;

import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class PageDomTest extends TestCase {

    public void testGetRedirectInContentAttr() throws Exception {
        String value = PageDom.getRedirectInContentAttr("0 ; URL=http://irmar.univ-rennes1.fr");
        MatcherAssert.assertThat(value, Matchers.equalTo("http://irmar.univ-rennes1.fr"));

    }
}