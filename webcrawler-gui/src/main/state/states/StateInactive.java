package state.states;

import state.KeyBoardListenerImpl;
import state.StateMachine;
import state.lib.AndroidScreen;
import state.lib.AndroidSound;

public class StateInactive implements State {
    private final StateMachine stateMachine;
    AndroidScreen androidScreen;
    AndroidSound androidSound;

    public StateInactive(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void redButton() {
        androidSound.smallBeep();
    }

    @Override
    public void greenButton() {
        stateMachine.setActiveWithSoundState();
    }

}