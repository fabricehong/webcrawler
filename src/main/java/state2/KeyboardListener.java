package state2;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public interface KeyboardListener {
    enum BUTTON {
        GREEN_BUTTON, RED_BUTTON;
    }
    void buttonPushed(BUTTON button);
}
