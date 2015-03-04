package state2;

import state2.libs.AndroidKeyboard;
import state2.libs.AndroidScreen;
import state2.libs.AndroidSound;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class AndroidApp {


    private final AndroidSound androidSound;
    private final AndroidScreen androidScreen;
    private final AndroidKeyboard androidKeyboard;
    private AndroidStateMachine androidStateMachine;

    public AndroidApp() {
        androidSound = new AndroidSound();
        androidScreen = new AndroidScreen();
        androidKeyboard = new AndroidKeyboard();
        androidStateMachine = new AndroidStateMachine(androidScreen, androidSound);

        initListeners();
    }

    private void initListeners() {
        androidKeyboard.registerKeyboardListener(new KeyboardListenerImpl(androidStateMachine));
    }

    public static void main(String[] args) {
        AndroidApp androidApp = new AndroidApp();

    }
}
