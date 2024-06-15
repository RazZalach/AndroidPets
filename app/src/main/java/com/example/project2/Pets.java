package com.example.project2;

public class Pets {
    private String type;
    private String name;
    private double age;
    private String color;
    private String picUrl;
    private String contactPhone;
    private boolean isAdopted;

    // Constructor
    public Pets(String type, String name, double age, String color, String picUrl, String contactPhone, boolean isAdopted) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.color = color;
        this.picUrl = picUrl;
        this.contactPhone = contactPhone;
        this.isAdopted = isAdopted;
    }

    // Default constructor
    public Pets() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public void setAdopted(boolean isAdopted) {
        this.isAdopted = isAdopted;
    }
}
