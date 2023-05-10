package com.studybuddy;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class studyState implements State, Serializable {
    private UserTimeState user;

    public studyState(UserTimeState user){
        this.user = user;
    }

    @Override
    public void startStudy() {
    }

    @Override
    public void pause() {
        this.user.setPauseState(new pauseState(this.user));
        this.user.setState(this.user.getPauseState());
    }

    @Override
    public void resume() {
    }

    @Override
    public void stopStudy() {
        this.user.setState(new idleState(this.user));
        this.user.setPauseState(null);
        this.user.setStudyState(null);
    }
}
