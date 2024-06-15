package com.example.project2;

public class Employee {
    private long id;
    private String NameEmp;
    private String PasswordEmp;
    private String EmailEmp;
    private String Job;
    private String Phone_num;
    private Farm farm;

    public Employee(long id, String nameEmp, String passwordEmp, String emailEmp, String job, String phone_num) {
        this.id = id;
        NameEmp = nameEmp;
        PasswordEmp = passwordEmp;
        EmailEmp = emailEmp;
        Job = job;
        Phone_num = phone_num;
    }

    public long getId() {
        return id;
    }

    public String getNameEmp() {
        return NameEmp;
    }

    public void setNameEmp(String nameEmp) {
        NameEmp = nameEmp;
    }

    public String getPasswordEmp() {
        return PasswordEmp;
    }

    public void setPasswordEmp(String passwordEmp) {
        PasswordEmp = passwordEmp;
    }

    public String getEmailEmp() {
        return EmailEmp;
    }

    public void setEmailEmp(String emailEmp) {
        EmailEmp = emailEmp;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getPhone_num() {
        return Phone_num;
    }

    public void setPhone_num(String phone_num) {
        Phone_num = phone_num;
    }
}
