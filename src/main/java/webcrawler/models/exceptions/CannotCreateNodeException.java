package webcrawler.models.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 8:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class CannotCreateNodeException extends Exception {
    public CannotCreateNodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
