package com.studybuddy;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.DoublePredicate;

public class User implements Serializable {
    private int uid;
    private String name;

    private String password;
    private boolean isUndergrad;

    private Map<Course, Double> courseTime;

    private double studyMinutes = 0.0;
    private int studyNumber = 0;

    // This set is used to store the unique courses that the user has studied
    private Set<Course> courseStudied = new HashSet<>();  // should I initialize it here?

    public Set<Course> getCourseStudied() {
        return courseStudied;
    }
    public void addCourseStudied(Course course) {
        this.courseStudied.add(course);
    }

    public int getStudyNumber() {
        return studyNumber;
    }
    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public Map<Course, Double> getCourseTime() {
        return courseTime;
    }

    public void addCourses(ArrayList<Course> courses){
        for(Course course:courses){
            this.courseTime.put(course, 0.0);
        }
    }

    // may be used later
    public void deleteCourse(Course course){
        this.courseTime.remove(course);
    }

    // add additional minutes to a course
    public void addCourseTime(Course course, Double additionalMinutes){
        this.courseTime.put(course, courseTime.get(course) + additionalMinutes);
    }

    public double getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(double studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
    public void addStudyMinutes(double studyMinutes) {
        this.studyMinutes += studyMinutes;
    }


    public User(int uid, String name, boolean isUndergrad, Map<Course, Double> courses) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = isUndergrad;
        this.courseTime = courses;

    }
    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = false;
        this.courseTime = new HashMap<>();
    }

//    public User(int uid, String name, String password) {
//        this.uid = uid;
//        this.name = name;
//        this.password = password;
//        this.isUndergrad = false;
//        this.courseTime = new HashMap<>();
//    }

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

    public String getPassword() {
        return password;
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
