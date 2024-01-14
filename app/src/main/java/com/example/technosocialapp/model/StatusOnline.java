package com.example.technosocialapp.model;

public class StatusOnline {
    private long idUser;
    private boolean status;
    private long date;

    public StatusOnline(long idUser, boolean status, long date) {
        this.idUser = idUser;
        this.status = status;
        this.date = date;
    }
    public StatusOnline() {

    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
