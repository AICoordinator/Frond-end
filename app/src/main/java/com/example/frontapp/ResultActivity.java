package com.example.frontapp;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

public class ResultActivity extends AppCompatActivity {

    private ViewPager pager;
    private PageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        pager = (ViewPager)findViewById(R.id.pager_images);

        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(this.getClass().getName(), "ResultActivity did");
            }
        });

        pagerAdapter = new PageAdapter(this);
        pager.setAdapter(pagerAdapter);

        TextView txt = (TextView)findViewById(R.id.countNum);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(this.getClass().getName(), "touchview touched");
            }
        });
    }


}