package com.example.technosocialapp.model;

public class Post {
    private long id;
    private long idInforUser;
    private String content;
    private long idPost;
    private long share;
    private long date;
    private int status;
    private int type;

    public long getShare() {
        return share;
    }

    public void setShare(long share) {
        this.share = share;
    }

    public Post(long id, long idInforUser, String content, long idPost, long share, long date, int status, int type) {
        this.id = id;
        this.idInforUser = idInforUser;
        this.content = content;
        this.idPost = idPost;
        this.share = share;
        this.date = date;
        this.status = status;
        this.type = type;
    }
    public Post() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
