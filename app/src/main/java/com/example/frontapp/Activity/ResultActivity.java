package com.example.frontapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.example.frontapp.Adapter.PageAdapter;
import com.example.frontapp.R;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.UserData.ResultStruct;

public class ResultActivity extends AppCompatActivity {

    private ViewPager pager;
    private PageAdapter pagerAdapter;
    private ImageView profileBtn, selectedBtn;
    private Button saveBtn;
    private TextView scoreView;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d("WOW", "RESULT ACTIVITY START!");
        profileBtn = findViewById(R.id.profileBtn);
        selectedBtn = findViewById(R.id.selectBtn);
        scoreView = findViewById(R.id.resultScore);
        DataManager dataManager = DataManager.getInstance();
        ResultStruct[] resultStructs = dataManager.getResultStructs();
        scoreView.setText(resultStructs[0].getResultScore());
        //View Pager 적용
        pager = (ViewPager)findViewById(R.id.pager_images);
        pagerAdapter = new PageAdapter(this);

        //주위 패딩 적용
        pager.setClipToPadding(false);
        pager.setPadding(100, 0, 100, 0);
        pager.setPageMargin(50);

        pager.setAdapter(pagerAdapter);

        //프로필 버튼 누를 시
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WOW", "profile button touched~");
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //동영상 선택 버튼 누를 시
        selectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataManager.select(pager.getCurrentItem())) { //선택된 경우
                    selectedBtn.setImageResource(R.drawable.selected);
                }
                else { //선택 해제하는 경우
                    selectedBtn.setImageResource(R.drawable.unselected);
                }
                Log.d("WOW", Integer.toString(pager.getCurrentItem()) + "is selected");
            }
        });

        //뷰 페이지 이동시
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //점수 나타낼 textview
                scoreView.setText(resultStructs[pager.getCurrentItem()].getResultScore());
                if(dataManager.isSelected(pager.getCurrentItem())) {
                    selectedBtn.setImageResource(R.drawable.selected);
                }
                else {
                    selectedBtn.setImageResource(R.drawable.unselected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}