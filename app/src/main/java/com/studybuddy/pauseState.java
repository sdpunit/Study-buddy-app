package com.studybuddy;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

public class pauseState implements State, Serializable {
    private User user;

    private Context context;

    public pauseState(User user){
        this.user = user;
    }

    @Override
    public void startStudy(Context context) {
        Toast.makeText(context, "You are already studying.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pause(Context context) {
        Toast.makeText(context, "You are already paused.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resume(Context context) {
        this.user.setState(this.user.getStudyState());
    }

    @Override
    public void stopStudy() {
        this.user.setState(new idleState(this.user));
        this.user.setPauseState(null);
        this.user.setStudyState(null);
    }
}
