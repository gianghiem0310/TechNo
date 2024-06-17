package com.example.technosocialapp.model;

import java.util.ArrayList;

public class BigPost {
    ArrayList<Post> arrayList;

    public BigPost(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }
    public BigPost() {

    }

    public ArrayList<Post> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }
}
