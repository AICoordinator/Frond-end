package com.example.frontapp;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

public class PageActivity extends AppCompatActivity {
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        imageView = findViewById(R.id.resultImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(this.getClass().getName(), "PageActivity did");
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationY", 0, 180);
                animator.setDuration(500);
                animator.start();
                imageView.setImageResource(R.drawable.aicoordinator_logo);
            }
        });
    }
}