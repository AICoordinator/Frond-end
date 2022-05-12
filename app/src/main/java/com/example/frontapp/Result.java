package com.example.frontapp;

import com.google.gson.annotations.SerializedName;
import retrofit2.http.Multipart;

import java.util.List;

public class Result {
    @SerializedName("email")
    private String email;

    @SerializedName("images")
    private List<Images> images;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}