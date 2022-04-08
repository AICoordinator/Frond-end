package com.example.frontapp.UserData;

import com.google.gson.annotations.SerializedName;

public class signUpRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private  String password;

    @SerializedName("gender")
    private int gender;

    @SerializedName("nickname")
    private String nickname;

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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public signUpRequest(String email, String password, int gender, String nickname) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
    }
}
