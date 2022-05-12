package com.example.frontapp;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("title")
    private String title;

    @SerializedName("score")
    private int score;

    @SerializedName("originImage")
    private String originImage;

    @SerializedName("changedImage")
    private String changedImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getOriginImage() {
        return originImage;
    }

    public void setOriginImage(String originImage) {
        this.originImage = originImage;
    }

    public String getChangedImage() {
        return changedImage;
    }

    public void setChangedImage(String changedImage) {
        this.changedImage = changedImage;
    }
}
