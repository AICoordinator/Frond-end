package com.example.frontapp.Communication;

import com.example.frontapp.Data.EraseImages;
import com.example.frontapp.Data.Images;
import com.example.frontapp.Data.Result;
import com.example.frontapp.UserData.ProfileRequest;
import com.example.frontapp.UserData.User;
import com.example.frontapp.UserData.loginRequest;
import com.example.frontapp.UserData.signUpRequest;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ServiceApi {
    @POST("/user/signup")
    Call<User> postData(@Body signUpRequest signUpRequest);

    @POST("/user/login")
    Call<User> loginServer(@Body loginRequest loginRequest);

    @Multipart
    @Streaming
    @POST("/user/result")
    Call<List<Images>> sendVideo(@Part MultipartBody.Part videoFile);

    @Streaming
    @GET("/user/profile")
    Call<List<Images>> getProfile();

    @POST("/user/profile")
    Call<Void> saveImage(@Body EraseImages images);
}