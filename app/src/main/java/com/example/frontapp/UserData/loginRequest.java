package com.example.frontapp.UserData;

import com.google.gson.annotations.SerializedName;

public class loginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public loginRequest(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
