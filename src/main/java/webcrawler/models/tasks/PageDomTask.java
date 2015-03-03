package webcrawler.models.tasks;

import webcrawler.models.parsing.PageDom;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/3/15
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PageDomTask {
    void doTask(PageDom pageDom);
}
