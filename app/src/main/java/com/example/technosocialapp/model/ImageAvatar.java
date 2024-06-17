package com.example.technosocialapp.model;

public class ImageAvatar {
    private long idUser;
    private String image;

    public ImageAvatar(long idUser, String image) {
        this.idUser = idUser;
        this.image = image;
    }
    public ImageAvatar() {

    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
