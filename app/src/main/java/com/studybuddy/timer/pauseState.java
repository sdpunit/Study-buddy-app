package com.studybuddy.timer;

import android.content.Context;

import java.io.Serializable;

/**
 * Pause state is the state when the user pauses the timer. He can only resume or stop studying in this state.
 */
public class pauseState implements State, Serializable {
    private UserTimeState user;

    private Context context;

    public pauseState(UserTimeState user){
        this.user = user;
    }

    @Override
    public void startStudy() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        this.user.setState(this.user.getStudyState());
    }

    @Override
    public void stopStudy() {
        this.user.setState(new idleState(this.user));
        this.user.setPauseState(null);
        this.user.setStudyState(null);
    }
}
