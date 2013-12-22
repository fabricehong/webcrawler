package state;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public interface KeyBoardListener {
    enum BUTTON {
        GREEN_BTN, RED_BTN
    }
    public void buttonPushed(BUTTON btn);
}
