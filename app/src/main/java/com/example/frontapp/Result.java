package com.example.frontapp;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("image1")
    private String image1;

    @SerializedName("image2")
    private String image2;

    @SerializedName("image3")
    private String image3;

    @SerializedName("image4")
    private String image4;

    @SerializedName("image5")
    private String image5;

    @SerializedName("image6")
    private String image6;

    @SerializedName("image7")
    private String image7;

    @SerializedName("image8")
    private String image8;

    @SerializedName("image9")
    private String image9;

    @SerializedName("image10")
    private String image10;

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public String getImage5() {
        return image5;
    }

    public String getImage6() {
        return image6;
    }

    public String getImage7() {
        return image7;
    }

    public String getImage8() {
        return image8;
    }

    public String getImage9() {
        return image9;
    }

    public String getImage10() {
        return image10;
    }

    public Result(String image1, String image2, String image3, String image4, String image5, String image6, String image7, String image8, String image9, String image10) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.image9 = image9;
        this.image10 = image10;
    }
}