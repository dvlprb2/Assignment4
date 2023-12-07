/*
  Filename: User.java
  Student's Name: Sinthuvamsan Viswarupan
  Student ID: 200557495
  Date: December 06, 2023
*/
package com.group6.assignment4;

public class User {
    private String name;
    private String email;
    private String password;
    private String token;

    public User() {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

