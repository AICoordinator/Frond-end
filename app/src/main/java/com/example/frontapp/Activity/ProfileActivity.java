package com.example.frontapp.Activity;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //여기에 통신 코드
        serviceApi = RetrofitClient.getRetrofitInterface();
        Log.d("WOW", "Token : " + TokenRepository.getAuthToken());
        ProfileRequest profileRequest = new ProfileRequest("royseo98@gmail.com");
        serviceApi.getProfile(profileRequest).enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                if(response.isSuccessful()) {
                    Log.d("WOW", "profile response successful");
                    dataManager = DataManager.getInstance();
                    dataManager.setTotalResultStructs(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Images>> call, Throwable t) {
                Log.d("WOW", "profile response failed");
            }
        });

        setContentView(R.layout.activity_profile);

        NewFragment newFragment = new NewFragment();
        UpFragment upFragment = new UpFragment();
        DownFragment downFragment = new DownFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

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