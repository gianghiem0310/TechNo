package com.example.technosocialapp.model;

import java.io.Serializable;

public class User{
    private long id;
    private String email;
    private String password;
    private boolean authentication;
    private int type;
    private long dateCreate;
    public User() {

    }
    public User(long id, String email, String password, boolean authentication, int type, long dateCreate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authentication = authentication;
        this.type = type;
        this.dateCreate = dateCreate;
    }

    public long getId() {
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

    public boolean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }
}
