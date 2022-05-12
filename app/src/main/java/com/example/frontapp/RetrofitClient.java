package com.example.frontapp;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static ServiceApi serviceApi;
    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = "http://7a89-58-234-175-160.ngrok.io/";

    public static String getBaseUrl() {
        return baseUrl;
    }


    private RetrofitClient() {
        //Intercepter 미완성 연결 안됨
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor( new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                return null;
            }
        }).build();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceApi = retrofit.create(ServiceApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static ServiceApi getRetrofitInterface() {
        return serviceApi;
    }
}
