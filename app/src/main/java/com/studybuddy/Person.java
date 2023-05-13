package com.studybuddy;

public class Person {
    public static void main(String[] args) {

    }

    public Person(int uid, String username, String password, boolean isUndergrad, int studyMinutes) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.isUndergrad = isUndergrad;
        this.studyMinutes = studyMinutes;
    }

    private int uid;
    private String username;
    private String password;
    private boolean isUndergrad;
    private int studyMinutes;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUndergrad() {
        return isUndergrad;
    }

    public void setUndergrad(boolean undergrad) {
        this.isUndergrad = undergrad;
    }

    public int getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
}
