package com.example.technosocialapp.model;

public class Comment {
    private long id;
    private long idPost;
    private long idInforUser;
    private String content;
    private int status;
    private long date;

    public Comment(long id, long idPost, long idInforUser, String content, int status, long date) {
        this.id = id;
        this.idPost = idPost;
        this.idInforUser = idInforUser;
        this.content = content;
        this.status = status;
        this.date = date;
    }
    public Comment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }

    public long getIdInforUser() {
        return idInforUser;
    }

    public void setIdInforUser(long idInforUser) {
        this.idInforUser = idInforUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
