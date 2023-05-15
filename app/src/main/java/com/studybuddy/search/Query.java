package com.studybuddy.search;

public class Query {

    private String college;

    private int code;

    private String course;

    private String convener;

    private String term;

    public Query() {
        this.college = null;
        this.code = 0;
        this.course = null;
        this.convener = null;
        this.term = null;
    }

    /*
     "course_code": "INTR2020",
      "course_name": "(In)Stability on the Korean Peninsula",
      "student_type": "Undergraduate",
      "assessment": [
        "1. Participation in Discussion - 10% (10) [LO null]",
        "2. Research Project - 50% (50) [LO null]",
        "3. Final Exam - 40% (40) [LO null]"
      ],
      "convener": []
     */

    public Query(String college, int code, String course, String convener, String term) {
        this.college = college;
        this.code = code;
        this.course = course;
        this.convener = convener;
        this.term = term;
    }

    // create hashmap to store course code with key access from convener and course name
    @Override
    public int hashCode() {
        return super.hashCode();
    }




    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getConvener() {
        return convener;
    }

    public void setConvener(String convener) {
        this.convener = convener;
    }
}
