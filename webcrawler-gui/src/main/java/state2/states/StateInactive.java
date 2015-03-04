package state2.states;

import state2.AndroidStateMachine;
import state2.libs.AndroidScreen;
import state2.libs.AndroidSound;

public class StateInactive implements State {
    private AndroidStateMachine androidStateMachine;
    private AndroidSound androidSound;

    public StateInactive(AndroidStateMachine androidStateMachine, AndroidSound androidSound) {
        this.androidStateMachine = androidStateMachine;
        this.androidSound = androidSound;
    }

    @Override
    public void greenButton() {
        androidStateMachine.setStateActiveWithSound();
    }

    @Override
    public void redButton() {
        androidSound.smallBeep();
    }

}