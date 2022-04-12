package com.example.frontapp;

import com.example.frontapp.UserData.User;
import com.example.frontapp.UserData.loginRequest;
import com.example.frontapp.UserData.signUpRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface ServiceApi {
    @POST("/user/signup")
    Call<User> postData(@Body signUpRequest signUpRequest);

    @POST("/user/login")
    Call<User> loginServer(@Body loginRequest loginRequest);
    Call<User> loginServer(@Body User user);

    @Multipart
    @POST("/user/result")
    Call<User> sendVideo(@Part("video") RequestBody video);
}
