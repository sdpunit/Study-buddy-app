package com.studybuddy;

public class Course {
    public User[] members;
    public int timeStudied;

    public Course(User[] members, int timeStudied) {
        this.members = members;
        this.timeStudied = timeStudied;
    }

    public User[] getMembers() {
        return members;
    }

    public void setMembers(User[] members) {
        this.members = members;
    }

    public int getTimeStudied() {
        return timeStudied;
    }

    public void setTimeStudied(int timeStudied) {
        this.timeStudied = timeStudied;
    }
}
