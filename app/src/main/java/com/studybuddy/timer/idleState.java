package com.studybuddy.timer;

import java.io.Serializable;

/**
 * Idle state is the initial state of the user. He can only start studying in this state.
 * @author Yanghe (u7533843)
 */
public class idleState implements State, Serializable {
    private UserTimeState userTimeState;

    public idleState(UserTimeState userTimeState){
        this.userTimeState = userTimeState;
    }

    @Override
    public void startStudy() {
        this.userTimeState.setStudyState(new studyState(this.userTimeState));
        this.userTimeState.setState(this.userTimeState.getStudyState());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void stopStudy() {
    }
}
