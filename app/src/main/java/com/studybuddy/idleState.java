package com.studybuddy;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

public class idleState implements State, Serializable {
    private User user;

    private Context context;
    public idleState(User user){
        this.user = user;
    }

    @Override
    public void startStudy(Context context) {
        this.user.setStudyState(new studyState(this.user));
        this.user.setState(this.user.getStudyState());
    }

    @Override
    public void pause(Context context) {

    }

    @Override
    public void resume(Context context) {
    }

    @Override
    public void stopStudy() {
    }
}
