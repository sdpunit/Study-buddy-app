package com.studybuddy.search;

/**
 * This class represents a search query.
 * @author Steven (u7108792)
 */
public class Query {
    private String college;
    private int code;
    private String course;
    private String convener;

    /**
     * This method constructs an empty query object.
     * @author Steven (u7108792)
     */
    public Query() {
        this.college = null;
        this.code = 0;
        this.course = null;
        this.convener = null;
    }

    /**
     * This method constructs a query with predefined values.
     * @author Steven (u7108792)
     */
    public Query(String college, int code, String course, String convener) {
        this.college = college;
        this.code = code;
        this.course = course;
        this.convener = convener;
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
