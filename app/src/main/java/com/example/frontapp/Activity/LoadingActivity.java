package com.example.frontapp.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontapp.Communication.RetrofitClient;
import com.example.frontapp.Communication.ServiceApi;
import com.example.frontapp.Data.Images;
import com.example.frontapp.Data.Result;
import com.example.frontapp.ProgressDialog;
import com.example.frontapp.R;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.UserData.ResultStruct;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LoadingActivity extends AppCompatActivity {

    RetrofitClient retrofitClient;
    ServiceApi serviceApi;
    Context mContext;
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        //retrofitClient = RetrofitClient.getInstance();
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
            //동영상 첨부
            RequestBody requestBody = RequestBody.create(MediaType.parse("video/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("video", file.getName(), requestBody);
            serviceApi.sendVideo(fileToUpload).enqueue(new Callback<List<Images>>() {
                @Override
                public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                    if(response.isSuccessful()) {
                        Log.d("WOW", "POST 성공!!!!!!!!!!");
                        //응답 객체 json형식으로 받기
                        List<Images> responseBody = response.body();

                        //result activity로 이동할 intent
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                        //image에 대핸 정보 객체만 따로 받기
                        List<Images> images = responseBody;
                        //string to byte stream using base64
                        //view pager로 넘길 정보 파싱
                        ResultStruct[] resultStructs = new ResultStruct[5];
                        try {
                            for (int i = 0; i < 5; i++) {
                                resultStructs[i] = new ResultStruct(
                                        Base64.decode(images.get(i).getOriginImage(), 0),
                                        Base64.decode(images.get(i).getChangedImage(), 0),
                                        images.get(i).getScore());
                                Log.d("WOW", images.get(i).getScore());
                            }
                            //singleton 객체 불러와서 이미지 정보 전부 저장
                            DataManager dataManager = DataManager.getInstance();
                            dataManager.setResultStructs(resultStructs);
                            //result activity로 이동
                            startActivity(intent);
                        }
                        catch(NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.d("WOW", "POST Failed!!!!!!!!!!");
                    }
                }

                @Override
                public void onFailure(Call<List<Images>> call, Throwable t) {
                    Log.d("FAIL MESSAGE", t.getMessage());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("FAIL MESSAGE", "SIBAL");
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