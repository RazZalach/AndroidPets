package com.example.project2;

import java.util.Date;

public class DiaryPage {
    private  int id;
    private String name;
    private String body;
    private Date dateCreated;

    private String Picurl;



    public DiaryPage(){

    }
    public DiaryPage(String name, String body, String picurl) {
        this.name = name;
        this.body = body;
        this.dateCreated = new Date();
        this.Picurl = picurl;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPicUrl() {
        return Picurl;
    }

    public void setPicUrl(String picurl) {
        this.Picurl = picurl;
    }

//    public byte[] getVoiceRecord() {
//        return voiceRecord;
//    }
//
//    public void setVoiceRecord(byte[] voiceRecord) {
//        this.voiceRecord = new byte[voiceRecord.length];
//        this.voiceRecord = voiceRecord;
//    }
}