package com.myclass.app;

public class studentClassModel {
    private String studentClassId;
    private String studentClassName;
    private String studentTeacherName;
    private String studentJoinCode;

    public studentClassModel(String studentClassId, String studentClassName, String studentTeacherName) {
        this.studentClassId = studentClassId;
        this.studentClassName = studentClassName;
        this.studentTeacherName = studentTeacherName;
       // this.studentJoinCode = studentJoinCode;
    }

    public String getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(String studentClassId) {
        this.studentClassId = studentClassId;
    }

    public String getStudentClassName() {
        return studentClassName;
    }

    public void setStudentClassName(String studentClassName) {
        this.studentClassName = studentClassName;
    }

    public String getStudentTeacherName() {
        return studentTeacherName;
    }

    public void setStudentTeacherName(String studentTeacherName) {
        this.studentTeacherName = studentTeacherName;
    }

    public String getStudentJoinCode() {
        return studentJoinCode;
    }

    public void setStudentJoinCode(String studentJoinCode) {
        this.studentJoinCode = studentJoinCode;
    }
}
