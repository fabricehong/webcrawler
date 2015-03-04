package state2;

import state2.libs.AndroidScreen;
import state2.libs.AndroidSound;
import state2.states.State;
import state2.states.StateActiveWithSound;
import state2.states.StateActiveWithoutSound;
import state2.states.StateInactive;

public class AndroidStateMachine {
    private State state;
    private State stateActiveWithSound;
    private State stateActiveWithoutSound;
    private State stateInactive;

    private AndroidScreen androidScreen;
    private AndroidSound androidSound;

    public AndroidStateMachine(AndroidScreen androidScreen, AndroidSound androidSound) {
        this.androidScreen = androidScreen;
        this.androidSound = androidSound;
        state = new StateInactive(this, androidSound);
    }

    // -------------------------------- events ----------------------

    void greenButton() {
        state.greenButton();
    }

    void redButton() {
        state.redButton();
    }

    // ---------------------------- state inbound ---------------------

    public void setStateActiveWithoutSound() {
        androidSound.stopBeep();
        androidScreen.lightOn();
        state = getOrCreateStateActiveWithoutSound();
    }

    public void setStateInactive() {
        androidScreen.lightOff();
        androidSound.stopBeep();
    }

    public void setStateActiveWithSound() {
        androidSound.beep();
        androidScreen.lightOn();
    }

    // ---------------------- private methods ----------------------------

    private State getOrCreateStateActiveWithoutSound() {
        if (stateActiveWithoutSound == null) {
            stateActiveWithoutSound = new StateActiveWithoutSound(this);
        }
        return stateActiveWithoutSound;
    }

    private State getOrCreateStateActiveWithSound() {
        if (stateActiveWithSound == null) {
            stateActiveWithSound = new StateActiveWithSound(this);
        }
        return stateActiveWithSound;
    }

    private State getOrCreateStateInactive() {
        if (stateInactive == null) {
            stateInactive = new StateInactive(this, androidSound);
        }
        return stateInactive;
    }
}