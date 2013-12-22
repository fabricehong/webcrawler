package state.states;

import state.StateMachine;
import state.lib.AndroidScreen;
import state.lib.AndroidSound;

public class StateActiveWithoutSound implements State {
    private final StateMachine stateMachine;
    AndroidScreen androidScreen;
    AndroidSound androidSound;

    public StateActiveWithoutSound(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void redButton() {
        stateMachine.setInactiveState();
    }

    @Override
    public void greenButton() {
        stateMachine.setActiveWithSoundState();
    }
}