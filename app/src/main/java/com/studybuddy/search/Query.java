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

    public Query(String college, int code, String course, String convener, String term) {
        this.college = college;
        this.code = code;
        this.course = course;
        this.convener = convener;
        this.term = term;
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
