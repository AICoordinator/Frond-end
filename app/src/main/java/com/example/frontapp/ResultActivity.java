package com.example.frontapp;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ViewPager pager;
    private PageAdapter pagerAdapter;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d("WOW", "RESULT ACTIVITY START!");

        //View Pager 적용
        pager = (ViewPager)findViewById(R.id.pager_images);
        pagerAdapter = new PageAdapter(this);

        //주위 패딩 적용
        pager.setClipToPadding(false);
        pager.setPadding(100, 0, 100, 0);
        pager.setPageMargin(50);

        pager.setAdapter(pagerAdapter);
    }


}