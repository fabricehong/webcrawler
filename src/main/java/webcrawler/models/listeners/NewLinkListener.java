package webcrawler.models.listeners;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 14.03.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public interface NewLinkListener {
    public void onNewLink(String fromLink, String toLink, String nodeName);
}
