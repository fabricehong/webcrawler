package webcrawler.core.listeners;

import webcrawler.core.parsing.PageDom;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public interface NewLinkListener {
    public void onVisitNewUrl(String fromLink, String toLink, PageDom pageDom);
}
