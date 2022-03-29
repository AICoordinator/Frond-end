package com.example.frontapp;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public class Data {
        @SerializedName("email")
        private int email;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
