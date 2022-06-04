package com.example.frontapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.session.MediaSession;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.Adapter.GridAdapter;
import com.example.frontapp.Communication.RetrofitClient;
import com.example.frontapp.Communication.ServiceApi;
import com.example.frontapp.Communication.TokenRepository;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.Data.Images;
import com.example.frontapp.Fragments.DownFragment;
import com.example.frontapp.Fragments.NewFragment;
import com.example.frontapp.Fragments.UpFragment;
import com.example.frontapp.ProgressDialog;
import com.example.frontapp.R;
import com.example.frontapp.UserData.ProfileRequest;
import com.example.frontapp.UserData.ResultStruct;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ServiceApi serviceApi;
    DataManager dataManager;
    NewFragment newFragment;
    UpFragment upFragment;
    DownFragment downFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //다이얼로그 띄워주기
        ProgressDialog progressDialog = new ProgressDialog(this);
        //백그라운드 투명하게
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //클릭해도 종료되지 않도록
        progressDialog.setCancelable(false);
        //로딩화면 보여주기
        progressDialog.show();

        //여기에 통신 코드
        serviceApi = RetrofitClient.getRetrofitInterface();
        Log.d("WOW", "Token : " + TokenRepository.getAuthToken());
        //ProfileRequest profileRequest = new ProfileRequest("royseo98@gmail.com");
        serviceApi.getProfile().enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                if(response.isSuccessful()) {
                    Log.d("WOW", "profile response successful");
                    dataManager = DataManager.getInstance();
                    dataManager.setTotalResultStructs(response.body());
                    newFragment = new NewFragment();
                    upFragment = new UpFragment();
                    downFragment = new DownFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Images>> call, Throwable t) {
                Log.d("WOW", "profile response failed");
            }
        });




        Log.d("WOW", "retrofit communication completed");

        BottomNavigationView btn = findViewById(R.id.navgation_bar);
        btn.setItemIconTintList(null);
        btn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.last:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();
                        return true;
                    case R.id.up:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, upFragment).commit();
                        return true;
                    case R.id.down:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, downFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

}