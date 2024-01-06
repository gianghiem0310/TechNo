package com.example.technosocialapp.model;

import java.io.Serializable;

public class User{
    private int id;
    private String email;
    private String password;
    private int authentication;
    private int type;
    private String dateCreate;
    public User() {

    }
    public User(int id, String email, String password, int authentication, int type, String dateCreate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authentication = authentication;
        this.type = type;
        this.dateCreate = dateCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
