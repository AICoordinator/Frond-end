package com.example.frontapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.R;
import com.example.frontapp.UserData.ResultStruct;

import java.io.ByteArrayInputStream;


public class PageAdapter extends PagerAdapter {

    private static final String TAG = "ShopNewsPagerAdapter";
    private Context context;
    private byte[][] bytearr;
    private ResultStruct[] resultStructs;
    boolean clicked = false;

    public PageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        //페이지 정보 불러오는 inflater
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_page, container , false);
        //이미지 나타낼 imageview
        ImageView imageView = view.findViewById(R.id.resultImage);
        //singleton으로부터 데이터 가져오기
        DataManager dataManager = DataManager.getInstance();
        resultStructs = dataManager.getResultStructs();
        //byte[] 데이터  stream 데이터로 변환 후 bitmapFactory로 이미지 생성
        ByteArrayInputStream orgStream = new ByteArrayInputStream(resultStructs[position].getOriginBytearr());
        ByteArrayInputStream modStream = new ByteArrayInputStream(resultStructs[position].getChangedBytearr());
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

        container.addView(view);

        return view;
    }
}
