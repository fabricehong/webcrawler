package state.states;

import state.StateMachine;
import state.lib.AndroidScreen;
import state.lib.AndroidSound;

public class StateActiveWithSound implements State {
    private final StateMachine stateMachine;
    public AndroidScreen androidScreen;
    public AndroidSound androidSound;

    public StateActiveWithSound(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void redButton() {
        stateMachine.setInactiveState();
    }

    @Override
    public void greenButton() {
        stateMachine.setActiveWithoutSoundState();

    }
}