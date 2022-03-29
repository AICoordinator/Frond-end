package com.example.frontapp;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private Boolean gender;

    @SerializedName("nickname")
    private String nickname;

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User(String email, String password, Boolean gender, String nickname) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
    }
}
