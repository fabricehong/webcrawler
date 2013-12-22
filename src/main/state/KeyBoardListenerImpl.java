package state;

import state.lib.AndroidScreen;
import state.lib.AndroidSound;
import state.states.State;

/**
* Created with IntelliJ IDEA.
* User: fabrice
* Date: 16.04.13
* Time: 20:31
* To change this template use File | Settings | File Templates.
*/
public class KeyBoardListenerImpl implements KeyBoardListener {
    private StateMachine stateMachine;


    KeyBoardListenerImpl(AndroidScreen androidScreen, AndroidSound androidSound) {
        stateMachine = new StateMachine(androidSound, androidScreen);
    }

    @Override
    public void buttonPushed(BUTTON btn) {
        switch(btn) {
            case GREEN_BTN:
                stateMachine.greenButton();
                break;
            case RED_BTN:
                stateMachine.redButton();
                break;
            default:
                throw new RuntimeException("Doesn't handle that button '"+btn+"'");
        }
    }


}
