package com.example.frontapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LoadingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

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


        class sendDataToHttp extends AsyncTask<Void, Void, String> {
            String serverUrl = RetrofitClient.getBaseUrl();
            OkHttpClient client = new OkHttpClient();
            Context context;

            public sendDataToHttp(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ContentResolver contentResolver = context.getContentResolver();
                final String contentType = contentResolver.getType(videoUri);
                final AssetFileDescriptor fd;
                try {
                    fd = contentResolver.openAssetFileDescriptor(videoUri, "r");
                    if (fd == null) {
                        throw new FileNotFoundException("could not open file descriptor");
                    }

                    RequestBody videoFile = new RequestBody() {
                        @Override
                        public long contentLength() {
                            return fd.getDeclaredLength();
                        }

                        @Override
                        public MediaType contentType() {
                            return MediaType.parse(contentType);
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            try (InputStream is = fd.createInputStream()) {
                                sink.writeAll(Okio.buffer(Okio.source(is)));
                            }
                        }
                    };

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("loginUserId", loginUserId)
                            .addFormDataPart("file", "fname",videoFile)
                            .build();

                    Request request = new Request.Builder()
                            .url(serverUrl)
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            try {
                                fd.close();
                            } catch (IOException ex) {
                                e.addSuppressed(ex);
                            }
                            Log.d("실패", "failed", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Log.d("결과",""+result);
                            fd.close();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }

        sendDataToHttp sendData = new sendDataToHttp(this);
        sendData.execute();


        //Dialog 종료
        //progressDialog.dismiss();
    }
}
