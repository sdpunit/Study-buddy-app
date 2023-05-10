package com.studybuddy;

import java.io.Serializable;

public class UserTimeState implements Serializable {
    private State pauseState;
    private State studyState;
    private State state;

    private double studyMinutes = 0.0;


    public UserTimeState(double studyMinutes) {
        this.studyMinutes = studyMinutes;
        this.state = new idleState(this);
    }
    public double getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(double studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
    public void addStudyMinutes(double studyMinutes) {
        this.studyMinutes += studyMinutes;
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
