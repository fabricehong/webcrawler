package webcrawler.core.url;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class UrlUtilsTest {

    @Test
    public void testGetHostFullUrl() throws Exception {
        String host = UrlUtils.getHostUrl("https://www.facebook.com/my/truc.html");
        assertThat(host, equalTo("https://www.facebook.com"));
    }

    @Test
    public void testGetHostWhenUrlIsDomain() throws Exception {
        String host = UrlUtils.getHostUrl("https://www.facebook.com");
        assertThat(host, equalTo("https://www.facebook.com"));
    }
}
