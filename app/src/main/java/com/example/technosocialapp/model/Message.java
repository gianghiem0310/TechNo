package com.example.technosocialapp.model;

public class Message {
    private long id;
    private long idUser;
    private String message;
    private long date;
    private boolean status;
    public Message() {

    }

    public Message(long id, long idUser, String message, long date, boolean status) {
        this.id = id;
        this.idUser = idUser;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
