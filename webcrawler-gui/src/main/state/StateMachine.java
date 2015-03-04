package state;

import state.lib.AndroidScreen;
import state.lib.AndroidSound;
import state.states.State;
import state.states.StateActiveWithSound;
import state.states.StateActiveWithoutSound;
import state.states.StateInactive;

public class StateMachine {
    private State currentState;
    private State activeWithSoundState;
    private State activeWithoutSoundState;
    private State inactiveState;

    private AndroidScreen androidScreen;
    private AndroidSound androidSound;

    public StateMachine(AndroidSound androidSound, AndroidScreen androidScreen) {
        this.androidSound = androidSound;
        this.androidScreen = androidScreen;
    }

    // ------------------ events ----------------------------------

    public void greenButton() {
        currentState.greenButton();;
    }

    public void redButton() {
        currentState.redButton();;
    }

    // --------------------- currentState inbound --------------------------

    public void setInactiveState() {
        this.androidScreen.lightOff();
        this.androidSound.stopBeep();

        setCurrentState(getInactiveState());
    }

    public void setActiveWithSoundState() {
        this.androidScreen.lightOn();
        this.androidSound.beep();

        setCurrentState(getActiveWithSoundState());
    }

    public void setActiveWithoutSoundState() {
        this.androidScreen.lightOn();
        androidSound.stopBeep();

        setCurrentState(getActiveWithoutSoundState());
    }

    // ---------------------- currentState lazy init -------------------

    private State getActiveWithoutSoundState() {
        if (activeWithoutSoundState == null) {
            activeWithoutSoundState = new StateActiveWithoutSound(null);
        }
        return activeWithoutSoundState;
    }

    private State getActiveWithSoundState() {
        if (activeWithSoundState == null) {
            activeWithSoundState = new StateActiveWithSound(null);
        }
        return activeWithSoundState;
    }



    private State getInactiveState() {
        if (inactiveState == null) {
            inactiveState = new StateInactive(null);
        }
        return inactiveState;
    }

    private void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

}