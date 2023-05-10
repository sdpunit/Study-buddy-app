package com.studybuddy;

import android.content.Context;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int uid;
    private String name;

    private String username;

    private String password;
    private boolean isUndergrad;
    private List<Course> courses;
    private State pauseState;
    private State studyState;
    private State state;

    private double studyMinutes = 0.0;

    public double getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(double studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
    public void addStudyMinutes(double studyMinutes) {
        this.studyMinutes += studyMinutes;
    }

    public State getPauseState() {
        return pauseState;
    }

    public void setPauseState(State pauseState) {
        this.pauseState = pauseState;
    }

    public State getStudyState() {
        return studyState;
    }

    public void setStudyState(State studyState) {
        this.studyState = studyState;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User(int uid, String name, boolean isUndergrad, List<Course> courses) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = isUndergrad;
        this.courses = courses;
        this.state = new idleState(this);
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = false;
        this.courses = null;
        this.state = new idleState(this);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsUndergrad() {
        return isUndergrad;
    }

    public void setIsUndergrad(boolean isUndergrad) {
        this.isUndergrad = isUndergrad;
    }

    public List<Course> getCourses() {
        return courses;
    }


    public void startStudy() {
        this.state.startStudy();
    }

    public void pause() {
        this.state.pause();
    }

    public void resume() {
        this.state.resume();
    }

    public void stopStudy() {
        this.state.stopStudy();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.getUid() == user.uid;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getUid() + ")";
    }
}
