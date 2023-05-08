package com.studybuddy;

import android.content.Context;

public interface State{
    public void startStudy(Context context);
    public void pause(Context context);
    public void resume(Context context);
    public void stopStudy();
}
