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
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
            serviceApi.sendVideo(fileToUpload).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(response.isSuccessful()) {
                        System.out.println("POST Success!!!!!!!!");
                        Result responseBody = response.body();
                        Log.d("TEST", "POST 성공!!!!!!!!!!");

                        //resultActivity로 이동
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        List<Images> images = responseBody.getImages();
                        Log.d("TEST", responseBody.getEmail());
                        String[] data = {images.get(0).getOriginImage(), images.get(0).getChangedImage(),
                                images.get(1).getOriginImage(), images.get(1).getChangedImage(),
                                images.get(2).getOriginImage(), images.get(2).getChangedImage(),
                                images.get(3).getOriginImage(), images.get(3).getChangedImage(),
                                images.get(4).getOriginImage(), images.get(4).getChangedImage()};
                        intent.putExtra("images", data);
                        startActivity(intent);
                    }
                    else {
                        Log.d("TEST", "POST Failed!!!!!!!!!!");
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.d("FAIL", t.getMessage());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean writeResponseBody(ResponseBody body, String path) {
        try {

            File file = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                //long fileSize = body.contentLength();
                //long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    //fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TEST", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
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
