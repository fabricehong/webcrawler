package webcrawler.models.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.03.13
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class NodeDoesntExists extends Exception {
    public NodeDoesntExists(String message) {
        super(message);
    }

    public NodeDoesntExists(String message, Throwable cause) {
        super(message, cause);
    }
}
