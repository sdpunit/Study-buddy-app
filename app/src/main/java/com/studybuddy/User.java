package com.studybuddy;

import java.util.List;

public class User {
    private int uid;
    private String name;
    private final Course[] courses;
    private List<User> friends;
    private boolean signedUp;

    public User(int uid, String name, Course[] courses, List<User> friends, boolean signedUp) {
        this.uid = uid;
        this.name = name;
        this.courses = courses;
        this.friends = friends;
        this.signedUp = signedUp;
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.courses = null;
        this.friends = null;
        this.signedUp = false;
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

    public Course[] getCourses() {
        return courses;
    }


    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public boolean isSignedUp() {
        return signedUp;
    }

    public void setSignedUp(boolean signedUp) {
        this.signedUp = signedUp;
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
