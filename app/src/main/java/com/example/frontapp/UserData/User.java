package com.example.frontapp.UserData;

import com.google.gson.annotations.SerializedName;

public class User {
    public static boolean isLogined = false;
    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private int gender;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("token")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(String email, String token, int gender, String nickname) {
        this.email = email;
        this.token = token;
        this.gender = gender;
        this.nickname = nickname;
    }



}
