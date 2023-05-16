package com.studybuddy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private int uid;
    private String name;
    private String password;
    private boolean isUndergrad;
    // To record the courses enrolled, because Map<Course, Double> is not accepted by firebase
    // for a hashmap to be stored for user, the key should be String and not object.
    private ArrayList<Course> coursesEnrolled;
    // This set is used to store the unique courses that the user has studied
    // It was initially a set, converted to an arraylist for firebase
    // Used to notify the user
    private ArrayList<Course> coursesStudied = new ArrayList<>();
    private Map<String, Double> courseTime;
    private double studyMinutes = 0.0;
    private int studyNumber = 0;

    // Need an empty constructor for firebase
    public User(){
        this.courseTime = new HashMap<>();
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = false;
        this.courseTime = new HashMap<>();
    }

    public User(int uid, String name, String password, boolean isUndergrad) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.isUndergrad = isUndergrad;
        this.courseTime = new HashMap<>();
    }

    public User(int uid, String name, boolean isUndergrad, ArrayList<Course> courses) {
        this.uid = uid;
        this.name = name;
        this.isUndergrad = isUndergrad;
        this.coursesStudied = courses;
        addCoursesTime(courses);
    }

    public ArrayList<Course> getCoursesStudied() {
        return coursesStudied;
    }

    public void addCourseStudied(Course course) {
        this.coursesStudied.add(course);
    }

    public void setCoursesStudied(ArrayList<Course> coursesStudied) {
        this.coursesStudied = coursesStudied;
        addCoursesTime(coursesStudied);
    }

    public ArrayList<Course> getCoursesEnrolled() {
        return coursesEnrolled;
    }
    public void addCoursesEnrolled(Course course) {
        this.coursesEnrolled.add(course);
        this.courseTime.put(course.getCourseCode(), 0.0);
    }
    public void setCoursesEnrolled(ArrayList<Course> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
        for (Course course : coursesEnrolled){
            this.courseTime.put(course.getCourseCode(), 0.0);
        }
    }

    public int getStudyNumber() {
        return studyNumber;
    }
    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public Map<String, Double> getCourseTime() {
        return courseTime;
    }

    public void addCoursesTime(ArrayList<Course> courses){
        for(Course course:courses){
            this.courseTime.put(course.getCourseCode(), 0.0);
        }
    }

    // may be used later
    public void deleteCourse(Course course){
        this.courseTime.remove(course);
    }

    // add additional minutes to a course
    public void addCourseTime(Course course, Double additionalMinutes){
        Double currentMinutes = courseTime.get(course.getCourseCode());
        if (currentMinutes == null) {
            currentMinutes = 0.0;
        }
        this.courseTime.put(course.getCourseCode(), currentMinutes + additionalMinutes);
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
