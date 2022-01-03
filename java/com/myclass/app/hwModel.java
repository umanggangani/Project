package com.myclass.app;

public class hwModel {
    public String getHwId() {
        return hwId;
    }

    private String hwId;
    private String hwName;
    private String hwDes;
    private String hwFile;
    private String hwDueDate;
    private String hwDate;

    public String getHwName() {
        return hwName;
    }

    public String getHwDes() {
        return hwDes;
    }

    public String getHwFile() {
        return hwFile;
    }

    public String getHwDueDate() {
        return hwDueDate;
    }

    public String getHwDate() {
        return hwDate;
    }

    public hwModel(String hwId, String hwName, String hwDes, String hwFile,String hwDueDate,String hwDate) {
        this.hwName = hwName;
        this.hwDes = hwDes;
        this.hwFile = hwFile;
        this.hwDueDate = hwDueDate;
        this.hwId = hwId;
        this.hwDate = hwDate;
    }
}
