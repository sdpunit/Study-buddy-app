package com.studybuddy.timer;

import java.io.Serializable;

public class idleState implements State, Serializable {
    private UserTimeState user;

    public idleState(UserTimeState user){
        this.user = user;
    }

    @Override
    public void startStudy() {
        this.user.setStudyState(new studyState(this.user));
        this.user.setState(this.user.getStudyState());
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
