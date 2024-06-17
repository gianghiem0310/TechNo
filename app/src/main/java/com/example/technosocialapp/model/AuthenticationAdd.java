package com.example.technosocialapp.model;

public class AuthenticationAdd {
    private long idSender;
    private String avatar;
    private String name;
    private long date;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AuthenticationAdd(long idSender, String avatar, String name, long date,boolean status) {
        this.idSender = idSender;
        this.avatar = avatar;
        this.name = name;
        this.date = date;
        this.status = status;
    }

    public long getIdSender() {
        return idSender;
    }

    public void setIdSender(long idSender) {
        this.idSender = idSender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public AuthenticationAdd() {

    }


}
