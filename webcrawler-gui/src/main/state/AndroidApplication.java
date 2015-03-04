package state;

import state.lib.AndroidKeyboard;
import state.lib.AndroidScreen;
import state.lib.AndroidSound;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class AndroidApplication {

    private AndroidKeyboard androidKeyboard;
    private AndroidScreen androidScreen;
    private AndroidSound androidSound;


    public AndroidApplication() {
        this.androidKeyboard = new AndroidKeyboard();
        this.androidScreen = new AndroidScreen();
        this.androidSound = new AndroidSound();

        KeyBoardListener eventListener = new KeyBoardListenerImpl(androidScreen, androidSound);
        this.androidKeyboard.register(eventListener);

    }

    public static void main(String[] args) {
        AndroidApplication androidApplication = new AndroidApplication();

    }

}
