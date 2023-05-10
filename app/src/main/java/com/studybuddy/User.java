package com.studybuddy;

import android.content.Context;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int uid;
    private String name;

    private boolean isUndergrad;
    private final Course[] courses;
    private Set<User> classmates;
    private boolean signedUp;
    private State pauseState;
    private State studyState;
    private State state;
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

    public User(int uid, String name, boolean isUndergrad, Course[] courses, Set<User> friends, boolean signedUp) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = isUndergrad;
        this.courses = courses;
        this.classmates = friends;
        this.signedUp = signedUp;
        this.state = new idleState(this);
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = false;
        this.courses = null;
        this.classmates = null;
        this.signedUp = false;
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

    public Course[] getCourses() {
        return courses;
    }


    public Set<User> getClassmates() {
        return classmates;
    }

    public void setClassmates(Set<User> classmates) {
        this.classmates = classmates;
    }

    public boolean isSignedUp() {
        return signedUp;
    }

    public void setSignedUp(boolean signedUp) {
        this.signedUp = signedUp;
    }

    public void startStudy(Context context) {
        this.state.startStudy(context);
    }

    public void pause(Context context) {
        this.state.pause(context);
    }

    public void resume(Context context) {
        this.state.resume(context);
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
