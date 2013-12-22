package state2.states;

import state2.AndroidStateMachine;

public class StateActiveWithSound implements State {
    AndroidStateMachine androidStateMachine;

    public StateActiveWithSound(AndroidStateMachine androidStateMachine) {
        this.androidStateMachine = androidStateMachine;
    }

    @Override
    public void greenButton() {
        androidStateMachine.setStateActiveWithoutSound();
    }

    @Override
    public void redButton() {
        androidStateMachine.setStateInactive();
    }

}