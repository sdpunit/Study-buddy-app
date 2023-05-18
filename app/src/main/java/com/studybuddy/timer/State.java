package com.studybuddy.timer;


/**
 * Interface for the state pattern. User in different states has different behaviour.
 * @author Yanghe (u7533843)
 */
public interface State{
    void startStudy();
    void pause();
    void resume();
    void stopStudy();
}
