package com.example.frontapp;

import com.example.frontapp.UserData.User;
import com.example.frontapp.UserData.loginRequest;
import com.example.frontapp.UserData.signUpRequest;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ServiceApi {
    @POST("/user/signup")
    Call<User> postData(@Body signUpRequest signUpRequest);

    @POST("/user/login")
    Call<User> loginServer(@Body loginRequest loginRequest);
    Call<User> loginServer(@Body User user);

    @Multipart
    @Streaming
    @POST("/user/result")
    Call<Result> sendVideo(@Part MultipartBody.Part videoFile);
}