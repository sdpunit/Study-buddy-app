package com.example;

public class User {
    public int uid;
    private final String username;
    private final String password;

    public String name;
    public Course[] courses;
    public User[] friends;
    public boolean signedin;

    public int key;

    public User(int uid, String name, Course[] courses, User[] friends, boolean signedin, String username, String password) {
        this.uid = uid;
        this.name = name;
        this.courses = courses;
        this.friends = friends;
        this.signedin = signedin;
        this.username = username;
        this.password = password;
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

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }

    public boolean isSignedin() {
        return signedin;
    }

    public void setSignedin(boolean signedin) {
        this.signedin = signedin;
    }
}
