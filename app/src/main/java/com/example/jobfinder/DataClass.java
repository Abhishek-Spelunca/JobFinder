package com.example.jobfinder;

public class DataClass {

    private String dataTitle;
    private String dataCompany;



    private String dataType;
    private String dataDesc;
    private String dataImage;


    public String getDataImage() {
        return dataImage;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataCompany() {
        return dataCompany;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataType() {
        return dataType;
    }

    public DataClass(String dataTitle, String dataCompany, String dataType, String dataDesc, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataCompany = dataCompany;
        this.dataType = dataType;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
    }

    public DataClass(){

    }
}
