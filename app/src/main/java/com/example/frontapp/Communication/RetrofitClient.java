package com.example.frontapp.Communication;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static ServiceApi serviceApi;
    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = "http://a57a-219-255-158-172.ngrok.io/";

    public static String getBaseUrl() {
        return baseUrl;
    }

    private RetrofitClient() {
        //Intercepter 미완성 연결 안됨
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().addHeader("Authorization",TokenRepository.getAuthToken()).build();
                    return chain.proceed(request);
                }).addInterceptor(httpLoggingInterceptor()).build();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
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

    private HttpLoggingInterceptor httpLoggingInterceptor(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                android.util.Log.e("WOW", message + "");
            }
        });

        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
