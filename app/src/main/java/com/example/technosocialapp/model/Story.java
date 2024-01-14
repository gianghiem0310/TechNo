package com.example.technosocialapp.model;

public class Story {
    private long id;
    private long idInforUser;
    private String image;
    private String content;
    private long date;

    public Story(long id, long idInforUser, String image, String content, long date) {
        this.id = id;
        this.idInforUser = idInforUser;
        this.image = image;
        this.content = content;
        this.date = date;
    }
    public Story() {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
