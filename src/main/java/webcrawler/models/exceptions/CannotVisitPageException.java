package webcrawler.models.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class CannotVisitPageException extends Exception {

    public CannotVisitPageException(String message, Throwable cause) {
        super(message, cause);
    }
}
