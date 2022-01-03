package com.myclass.app;

public class classModel {

    private String c_id;
    private String className;
    private String classDes;
    private String classDate;
    private  String classCode;

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getC_id() {
        return c_id;
    }

    public String getClassName() {
        return className;
    }

    public String getClassDes() {
        return classDes;
    }

    public String getClassDate() {
        return classDate;
    }

    public classModel(String c_id, String className, String classDes, String classDate,String classCode) {
        this.className = className;
        this.classDes = classDes;
        this.classDate = classDate;
        this.c_id = c_id;
        this.classCode = classCode;
    }
}
