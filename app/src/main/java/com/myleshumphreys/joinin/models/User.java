package com.myleshumphreys.joinin.models;

public class User {
    private String username;
    private String emailAddress;
    private String password;

    public User(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
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
}