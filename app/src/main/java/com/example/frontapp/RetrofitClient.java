package com.example.frontapp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://0b42-59-15-25-132.ngrok.io/";

    public static ServiceApi getApiService() {return getInstance().create(ServiceApi.class);}

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}