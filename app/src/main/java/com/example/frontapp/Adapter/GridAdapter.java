package com.example.frontapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.Data.Images;
import com.example.frontapp.R;

import java.io.ByteArrayInputStream;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Images> imagesList;

    public GridAdapter() {
        imagesList = DataManager.getTotalResultStructs();
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, viewGroup, false);
        }

        ImageView imageView = view.findViewById(R.id.imageView);
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(imagesList.get(i).getOriginImage(), 0));
        Bitmap bm = BitmapFactory.decodeStream(stream);
        imageView.setImageBitmap(bm);

        return view;
    }
}
