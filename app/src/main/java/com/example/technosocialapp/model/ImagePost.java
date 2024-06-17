package com.example.technosocialapp.model;

public class ImagePost {
    private long idPost;
    private String image;

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ImagePost(long idPost, String image) {
        this.idPost = idPost;
        this.image = image;
    }
    public ImagePost() {

    }
}
