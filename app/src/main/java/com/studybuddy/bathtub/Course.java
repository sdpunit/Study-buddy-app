package com.studybuddy.bathtub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Course class to store information for one course
 * @author Yanghe (u7533843), Punit (u7432723)
 */
public class Course implements Serializable {
    private String courseCode;  // key
    private String courseName;
    private String convener;
    private String studentType;
    private List<String> assessment;

    // Empty constructer for firebase needed
    public Course() {
    }

    public Course(String courseCode) {
        this.courseCode = courseCode;
    }

    Course(String courseCode, String courseName, String convener) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.convener = convener;
    }

    public Course(String courseCode, String courseName, String studentType, ArrayList<String> assessment, String convener) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.convener = convener;
        this.studentType = studentType;
        this.assessment = assessment;
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
    public String getStudentType() {
        return studentType;
    }
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }
    public List<String> getAssessment() { return assessment; }

    public void setAssessment(List<String> assessment) {
        this.assessment = assessment;
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
