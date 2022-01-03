package com.myclass.app;

public class meetModel {

    private String meetId;
    private String meetName;
    private String meetDes;
    private String meetLink;
    private String meetDate;
    private String meetSTime;
    private String meetETime;

    public meetModel(String meetId,String meetName, String meetDes, String meetLink, String meetDate, String meetSTime, String meetETime) {
        this.meetName = meetName;
        this.meetDes = meetDes;
        this.meetLink = meetLink;
        this.meetDate = meetDate;
        this.meetSTime = meetSTime;
        this.meetETime = meetETime;
        this.meetId = meetId;
    }
    public String getMeetId() {
        return meetId;
    }

    public String getMeetName() {
        return meetName;
    }

    public String getMeetDes() {
        return meetDes;
    }

    public String getMeetLink() {
        return meetLink;
    }

    public String getMeetDate() {
        return meetDate;
    }

    public String getMeetSTime() {
        return meetSTime;
    }

    public String getMeetETime() {
        return meetETime;
    }
}
