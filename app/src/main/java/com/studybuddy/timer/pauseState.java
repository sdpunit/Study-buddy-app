package com.studybuddy.timer;

import android.content.Context;

import java.io.Serializable;

/**
 * Pause state is the state when the user pauses the timer. He can only resume or stop studying in this state.
 * @author Yanghe (u7533843)
 */
public class pauseState implements State, Serializable {
    private UserTimeState userTimeState;

    private Context context;

    public pauseState(UserTimeState userTimeState){
        this.userTimeState = userTimeState;
    }

    @Override
    public void startStudy() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        this.userTimeState.setState(this.userTimeState.getStudyState());
    }

    @Override
    public void stopStudy() {
        this.userTimeState.setState(new idleState(this.userTimeState));
        this.userTimeState.setPauseState(null);
        this.userTimeState.setStudyState(null);
    }
}
