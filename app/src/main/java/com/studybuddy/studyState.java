package com.studybuddy;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class studyState implements State, Serializable {
    private User user;

    private Context context;
    public studyState(User user){
        this.user = user;
    }

    @Override
    public void startStudy(Context context) {
        Toast.makeText(context, "You are already studying.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pause(Context context) {
        this.user.setPauseState(new pauseState(this.user));
        this.user.setState(this.user.getPauseState());
    }

    @Override
    public void resume(Context context) {
        Toast.makeText(context, "You are not paused.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopStudy() {
        this.user.setState(new idleState(this.user));
        this.user.setPauseState(null);
        this.user.setStudyState(null);
    }
}
