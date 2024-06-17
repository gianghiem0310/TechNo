package com.example.technosocialapp.model;

public class ImageBackground {
    private long idUser;
    private String image;

    public ImageBackground(long idUser, String image) {
        this.idUser = idUser;
        this.image = image;
    }
    public ImageBackground() {

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
