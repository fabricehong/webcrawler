package state2;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class KeyboardListenerImpl implements KeyboardListener {


    private final AndroidStateMachine androidStateMachine;

    public KeyboardListenerImpl(AndroidStateMachine androidStateMachine) {
        this.androidStateMachine = androidStateMachine;
    }

    @Override
    public void buttonPushed(BUTTON button) {

         switch (button) {
             case GREEN_BUTTON:
                 androidStateMachine.greenButton();
                 break;
             case RED_BUTTON:
                 androidStateMachine.redButton();
                 break;
             default:
                 throw new RuntimeException("Unmanaged button: " + button);
         }
    }

}
