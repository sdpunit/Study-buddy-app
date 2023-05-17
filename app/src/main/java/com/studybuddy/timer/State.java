package com.studybuddy.timer;


/**
 * Interface for the state pattern
 * @author Yanghe Dong
 */
public interface State{
    public void startStudy();
    public void pause();
    public void resume();
    public void stopStudy();
}
