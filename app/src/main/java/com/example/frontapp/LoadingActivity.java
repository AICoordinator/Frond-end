package com.example.frontapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontapp.UserData.User;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class LoadingActivity extends AppCompatActivity {

    RetrofitClient retrofitClient;
    ServiceApi serviceApi;
    Context mContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mContext = this;
        //다이얼로그 띄워주기
        ProgressDialog progressDialog = new ProgressDialog(this);
        //백그라운드 투명하게
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //클릭해도 종료되지 않도록
        progressDialog.setCancelable(false);
        //로딩화면 보여주기
        progressDialog.show();

        //video uri 받아서 backend로 동영상 넘기기
        Intent intent = getIntent();
        String videoStr = intent.getStringExtra("videoUri");
       Log.d("VIDEO STRING : ", videoStr);
        Uri videoUri = Uri.parse(videoStr);

        //서버 접근 api선언
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        //동영상 불러오기
        ContentResolver contentResolver = this.getApplicationContext().getContentResolver();
        String contentType = contentResolver.getType(videoUri);

        final AssetFileDescriptor fd;
        try {
            fd = contentResolver.openAssetFileDescriptor(videoUri, "r");
            if (fd == null) { //file open 실피
                throw new FileNotFoundException("could not open file descriptor");
            }
            String abPath = getPath(videoUri);
            File file = new File(abPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("video/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("video", file.getName(), requestBody);
            System.out.println("aaaaa");
            serviceApi.sendVideo(fileToUpload).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        System.out.println("POST Success");
                        User data = response.body();
                        Log.d("TEST", "POST 성공");
                        Log.d("TEST", data.getEmail());
                    }
                    else {
                        Log.d("TEST", "POST Failed");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("FAIL", t.getMessage());
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 절대 경로 필요함!!
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));


        //some extra potentially useful data to help with filtering if necessary
        System.out.println("size: " + fileSize);
        System.out.println("path: " + filePath);
        System.out.println("duration: " + duration);

        return filePath;
    }

}
