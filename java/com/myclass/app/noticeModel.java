package com.myclass.app;

public class noticeModel {
    public String getNoticeId() {
        return noticeId;
    }

    private String noticeId;
    private String noticeName;
    private String noticeDes;
    private String noticeFile;
    private String noticeDate;

    public String getNoticeDate() {
        return noticeDate;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public String getNoticeDes() {
        return noticeDes;
    }

    public String getNoticeFile() {
        return noticeFile;
    }
    
    public noticeModel(String noticeId,String noticeName, String noticeDes, String noticeFile, String noticeDate) {
        this.noticeName = noticeName;
        this.noticeDes = noticeDes;
        this.noticeFile = noticeFile;
        this.noticeDate = noticeDate;
        this.noticeId = noticeId;
       
    }
}
