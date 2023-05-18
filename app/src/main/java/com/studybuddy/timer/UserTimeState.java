package com.studybuddy.timer;

import java.io.Serializable;

/**
 * This class is used to store the state of a user during a study session.
 * @author Punit (u7432723)
 */
public class UserTimeState implements Serializable {
    private State pauseState;
    private State studyState;
    private State state;

    public UserTimeState() {
        this.state = new idleState(this);
    }
    public State getPauseState() {
        return pauseState;
    }

    public void setPauseState(State pauseState) {
        this.pauseState = pauseState;
    }

    public State getStudyState() {
        return studyState;
    }

    public void setStudyState(State studyState) {
        this.studyState = studyState;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public void startStudy() {
        this.state.startStudy();
    }

    public void pause() {
        this.state.pause();
    }

    public void resume() {
        this.state.resume();
    }

    public void stopStudy() {
        this.state.stopStudy();
    }
}
