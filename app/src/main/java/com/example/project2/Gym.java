package com.example.project2;

public class Gym {
    private  String Name;
    private  String Address;
    private  Double[] Coordinates;
    private  String PicUrl;

    public Gym(String name, String address, Double[] coordinates, String picUrl) {
        Name = name;
        Address = address;
        Coordinates = coordinates;
        PicUrl = picUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double[] getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        Coordinates = coordinates;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
