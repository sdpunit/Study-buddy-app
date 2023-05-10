package com.studybuddy;

import java.util.Set;

public class Course {
    private String courseCode;  // key
    private String courseName;
    private String convener;

    private int studyMinutes;

    Course(String courseCode) {
        this.courseCode = courseCode;
    }
    Course(String courseCode, String courseName, String convener) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.convener = convener;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getConvener() {
        return convener;
    }

    public void setConvener(String convener) {
        this.convener = convener;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            Course course = (Course) obj;
            return courseCode.equals(course.getCourseCode());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", convener='" + convener +
                '}';
    }
}
