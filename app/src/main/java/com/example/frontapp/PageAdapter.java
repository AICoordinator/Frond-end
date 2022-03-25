package com.example.frontapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class PageAdapter extends PagerAdapter {

    private static final String TAG = "ShopNewsPagerAdapter";
    private int[] images = {R.drawable.aistylist_logo, R.drawable.aistylist_logo};
    private Context context;

    public PageAdapter(Context context) {
        this.context = context;
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
        imageView.setImageResource(images[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(this.getClass().getName(), "On Click Listener Worked!");
                imageView.setImageResource(R.drawable.aicoordinator_logo);
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
