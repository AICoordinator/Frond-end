package com.example.frontapp.Activity;

import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.Adapter.GridAdapter;
import com.example.frontapp.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GridView gridView = findViewById(R.id.gridView);

        //여기에 통신 코드
        GridAdapter adapter = new GridAdapter();

        gridView.setAdapter(adapter);
    }
}