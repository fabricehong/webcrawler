package state2.states;

import state2.AndroidStateMachine;
import state2.libs.AndroidScreen;
import state2.libs.AndroidSound;

public class StateActiveWithoutSound implements State {

    AndroidStateMachine androidStateMachine;

    public StateActiveWithoutSound(AndroidStateMachine androidStateMachine) {
        this.androidStateMachine = androidStateMachine;
    }

    @Override
    public void greenButton() {
        androidStateMachine.setStateActiveWithSound();
    }

    @Override
    public void redButton() {
        androidStateMachine.setStateInactive();
    }

}