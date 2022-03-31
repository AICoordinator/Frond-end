package com.example.frontapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface ServiceApi {
    @GET("/user/signup")
    Call<List<User>> getData(@Query("email") String id);

    @POST("/user/signup")
    Call<User> postData(@Body User data);

}
