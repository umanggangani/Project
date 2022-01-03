package com.myclass.app;

public class studentModel {

    private String s_id;
    private String studentName;
    private String studentCont;
    private String studentEmail;
    private String studentDate;

    public String getStudentDate() {
        return studentDate;
    }

    public void setStudentDate(String studentDate) {
        this.studentDate = studentDate;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCont() {
        return studentCont;
    }

    public void setStudentCont(String studentCont) {
        this.studentCont = studentCont;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public studentModel(String st_id, String studentName, String studentCont, String studentEmail,String studentDate) {
        this.s_id = st_id;
        this.studentName = studentName;
        this.studentCont = studentCont;
        this.studentEmail = studentEmail;
        this.studentDate = studentDate;
    }
}
