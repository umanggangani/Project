package com.myclass.app;

public class videoModel {
    public String getVideoId() {
        return videoId;
    }

    private String videoId;
    private String videoName;
    private String videoDes;
    private String videoLink;
    private String videoDate;
    private String videoSTime;
    private String videoETime;

    public videoModel(String videoId, String videoName, String videoDes, String videoLink, String videoDate, String videoSTime, String videoETime) {
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoDes = videoDes;
        this.videoLink = videoLink;
        this.videoDate = videoDate;
        this.videoSTime = videoSTime;
        this.videoETime = videoETime;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoDes() {
        return videoDes;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public String getVideoSTime() {
        return videoSTime;
    }

    public String getVideoETime() {
        return videoETime;
    }
}
