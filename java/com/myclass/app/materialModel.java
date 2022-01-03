package com.myclass.app;

public class materialModel {
    public String getMaterialId() {
        return materialId;
    }

    private String materialId;
    private String materialName;
    private String materialDes;
    private String materialFile;
    private String materialDate;
   
    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialDes() {
        return materialDes;
    }

    public String getMaterialFile() {
        return materialFile;
    }

    public String getMaterialDate() {
        return materialDate;
    }

    public materialModel(String materialId, String materialName, String materialDes, String materialFile, String materialDate) {
        this.materialName = materialName;
        this.materialDes = materialDes;
        this.materialFile = materialFile;
        this.materialId = materialId;
        this.materialDate = materialDate;
        
    }
}
