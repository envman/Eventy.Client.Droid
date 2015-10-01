package com.myleshumphreys.joinin.models;

public class User {
    private int id;
    private String username;
    private String emailAddress;
    private String password;
    private String token;

    public User(int id, String username, String emailAddress, String password, String token) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.token = token;
    }

    public int getId(){
        return this.id;
    }

    public String getUserName() {
        return this.username;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public String getToken() {
        return this.token;
    }
}