package com.studybuddy;

public class Course {
    int courseID;
    private String name;
    private RBTree courseStudents;

    public Course(int courseID, String name, RBTree courseStudents) {
        this.courseID = courseID;
        this.name = name;
        this.courseStudents = courseStudents;
    }

    public Course(int courseID, String name) {
        this.courseID = courseID;
        this.name = name;
        this.courseStudents =null;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RBTree getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(RBTree courseStudents) {
        this.courseStudents = courseStudents;
    }
}
