package com.example.technosocialapp.model;

public class Like {
    private long idPost;
    private long idInforUser;

    public Like(long idPost, long idInforUser) {
        this.idPost = idPost;
        this.idInforUser = idInforUser;
    }
    public Like() {

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
}
