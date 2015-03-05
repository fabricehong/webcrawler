package webcrawler.core.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import webcrawler.core.exceptions.CannotVisitPageException;
import webcrawler.core.parsing.PageDom;
import webcrawler.core.parsing.PageParser;
import webcrawler.core.tasks.PageDomTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/5/15
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisitUrlTaskTest {


    @Mock
    private WebCrawlingMainTask webCrawlingMainTask;

    private LinkCrawlingState linkCrawlingState;

    @Before
    public void setUp() throws CannotVisitPageException {
        initMocks(this);

        this.linkCrawlingState = new LinkCrawlingState();
    }

    @Test
    public void testSubmitOneLink() throws Exception {
        String fromUrl = "http://www.facebook.com";
        String urlToExplore = "http://www.facebook.com/main";
        PageVisitTester visitTask = createVisitTask(fromUrl, urlToExplore);
        visitTask.whenGetLinksReturn("sdf");
        visitTask.visitPage();

        assertVisitedLinksCountEquals(1);
        List<Collection> tasks = retrieveSubmittedTasks();
        assertThat(tasks.size(), equalTo(1));
    }

    @Test
    public void testOneLinkCannotBeVisitedTwoTimes() throws Exception {

        String fromUrl = "http://www.facebook.com";
        String urlToExplore = "http://www.facebook.com/main";
        PageVisitTester visitTask = createVisitTask(fromUrl, urlToExplore);
        visitTask.visitPage();
        assertVisitedLinksCountEquals(1);

        String fromUrl2 = "http://www.anotherUrl.com";
        PageVisitTester visitTask2 = createVisitTask(fromUrl2, urlToExplore);
        visitTask2.visitPage();
        assertVisitedLinksCountEquals(1);

    }

    private void assertVisitedLinksCountEquals(int number) {
        int linkCollectionSize = retrieveVisitedLinksCount();
        assertThat(linkCollectionSize, equalTo(number));
    }

    private int retrieveVisitedLinksCount() {
        return this.linkCrawlingState.getLinkCollectionSize();
    }

    private List<Collection> retrieveSubmittedTasks() {
        ArgumentCaptor<Collection> captor = ArgumentCaptor.forClass(Collection.class);
        Mockito.verify(this.webCrawlingMainTask).submitAllTasks(captor.capture());
        return captor.getAllValues();
    }

    private PageVisitTester createVisitTask(String fromUrl, String urlToExplore) {
        PageVisitTester pageVisitTester = null;
        try {
            pageVisitTester = new PageVisitTester();
        } catch (CannotVisitPageException e) {
            throw new RuntimeException(e);
        }
        VisitUrlTask visitUrlTask = new VisitUrlTask(webCrawlingMainTask, pageVisitTester.getPageDomTasks(), pageVisitTester.getPageParser(), fromUrl, urlToExplore, linkCrawlingState);
        pageVisitTester.setVisitUrlTask(visitUrlTask);
        return pageVisitTester;
    }


}

class PageVisitTester {

    private List<PageDomTask> pageDomTasks;

    @Mock
    private PageParser pageParser;

    @Mock
    private PageDom pageDom;

    private VisitUrlTask visitUrlTask;

    public PageVisitTester() throws CannotVisitPageException {
        initMocks(this);
        when(pageParser.parsePage(anyString())).thenReturn(pageDom);
        this.pageDomTasks = new ArrayList<>();

        // default setup
        whenGetLinksReturn();
    }

    public void whenGetLinksReturn(String... links) {
        Elements linkElements = new Elements();
        for (String link : links) {
            Element elem = Mockito.mock(Element.class);
            when(elem.attr(Mockito.eq("href"))).thenReturn(link);
            linkElements.add(elem);
        }
        when(pageDom.getLinks()).thenReturn(linkElements);
    }

    public void visitPage() {
        try {
            this.visitUrlTask.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    PageParser getPageParser() {
        return pageParser;
    }

    PageDom getPageDom() {
        return pageDom;
    }

    VisitUrlTask getVisitUrlTask() {
        return visitUrlTask;
    }

    void setVisitUrlTask(VisitUrlTask visitUrlTask) {
        this.visitUrlTask = visitUrlTask;
    }

    List<PageDomTask> getPageDomTasks() {
        return pageDomTasks;
    }
}
