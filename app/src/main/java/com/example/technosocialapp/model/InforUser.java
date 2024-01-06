package com.example.technosocialapp.model;

public class InforUser {
    private int id;
    private String avatar;
    private String name;
    private String date;
    private String sex;
    private String address;
    private String job;
    private String company;
    private String favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public InforUser(int id, String avatar, String name, String date, String sex, String address, String job, String company, String favorite) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.date = date;
        this.sex = sex;
        this.address = address;
        this.job = job;
        this.company = company;
        this.favorite = favorite;
    }
    public InforUser() {

    }
}
