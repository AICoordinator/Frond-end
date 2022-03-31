package com.example.frontapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface ServiceApi {
    @FormUrlEncoded
    @POST("/user/signup")
    Call<User> postData(@FieldMap HashMap<String, Object> data);

}
