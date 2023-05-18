package com.studybuddy.timer;

import java.io.Serializable;

/**
 * Study state is the state when the user is studying. He can only pause or stop studying in this state.
 * @author Yanghe
 */
public class studyState implements State, Serializable {
    private UserTimeState userTimeState;

    public studyState(UserTimeState userTimeState){
        this.userTimeState = userTimeState;
    }

    @Override
    public void startStudy() {
    }

    @Override
    public void pause() {
        this.userTimeState.setPauseState(new pauseState(this.userTimeState));
        this.userTimeState.setState(this.userTimeState.getPauseState());
    }

    @Override
    public void resume() {
    }

    @Override
    public void stopStudy() {
        this.userTimeState.setState(new idleState(this.userTimeState));
        this.userTimeState.setPauseState(null);
        this.userTimeState.setStudyState(null);
    }
}
