package com.example.frontapp;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

public class ResultActivity extends AppCompatActivity {

    private ViewPager pager;
    private PageAdapter pagerAdapter;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Video Uri 불러오기
        Intent getVideo = getIntent();
        videoUri = Uri.parse(getVideo.getStringExtra("videoUri"));

        //View Pager 적용
        pager = (ViewPager)findViewById(R.id.pager_images);
        pagerAdapter = new PageAdapter(this);
        pager.setAdapter(pagerAdapter);
    }


}