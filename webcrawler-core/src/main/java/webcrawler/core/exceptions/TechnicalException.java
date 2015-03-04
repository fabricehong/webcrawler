package webcrawler.core.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/3/15
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TechnicalException extends RuntimeException {

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public TechnicalException(String message) {
        super(message);
    }
}
