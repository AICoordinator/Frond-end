package com.example.frontapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class PageAdapter extends PagerAdapter {

    private static final String TAG = "ShopNewsPagerAdapter";
    private Context context;
    private String[] images;
    boolean clicked = false;

    public PageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View)object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_page, container , false);
        ImageView imageView = view.findViewById(R.id.resultImage);

        //ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //byte[] image = outStream.toByteArray();
        // base64String 데이터 -> stream데이터 -> image데이터
        String originImage = images[position * 2];
        String modifiedImage = images[position * 2 + 1];
        //데이터 base64 형식으로 Decode
        byte[] bytePlainOrg = Base64.decode(originImage, 0);
        byte[] bytePlainMod = Base64.decode(modifiedImage, 0);

        //byte[] 데이터  stream 데이터로 변환 후 bitmapFactory로 이미지 생성
        ByteArrayInputStream orgStream = new ByteArrayInputStream(bytePlainOrg);
        ByteArrayInputStream modStream = new ByteArrayInputStream(bytePlainMod);
        Bitmap originBm = BitmapFactory.decodeStream(orgStream);
        Bitmap modifiedBm = BitmapFactory.decodeStream(modStream);

        imageView.setImageBitmap(originBm);

        //사진 클릭시 원래 사진 <-> 수정된 사진 보내기
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(this.getClass().getName(), "On Click Listener Worked!");
                clicked = (clicked) ? false : true;
                if(clicked) { //수정된 이미지 보여주기
                    imageView.setImageBitmap(modifiedBm);
                }
                else { //원본 이미지 보여주기
                    imageView.setImageBitmap(originBm);
                }
            }
        });

        LayoutInflater resultInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View resultView = inflater.inflate(R.layout.activity_result, container, false);
        TextView textView = resultView.findViewById(R.id.countNum);
        textView.setText(position + " / " + getCount());

        container.addView(view);

        return view;
    }
}
