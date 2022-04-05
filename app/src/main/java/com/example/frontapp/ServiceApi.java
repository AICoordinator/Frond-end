package com.example.frontapp;

import com.example.frontapp.UserData.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface ServiceApi {
    @FormUrlEncoded
    @POST("/user/signup")
    Call<User> postData(@FieldMap HashMap<String, Object> data);

    @POST("/user/login")
    Call<User> loginServer(@Body User user);
}
