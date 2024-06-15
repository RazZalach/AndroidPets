package com.example.project2;

public class User {
    private String email;
    private int id;
    private String userName;
    private String password;

    public User(String email, int id, String userName, String password) {
        this.email = email;
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
    public User(String email,  String userName, String password) {
        this.email = email;
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
