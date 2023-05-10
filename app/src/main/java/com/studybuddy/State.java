package com.studybuddy;

import android.content.Context;

public interface State{
    public void startStudy();
    public void pause();
    public void resume();
    public void stopStudy();
}
