package com.example.frontapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.frontapp.Adapter.GridAdapter;
import com.example.frontapp.Data.DataManager;
import com.example.frontapp.R;

public class NewFragment extends Fragment {

    private View view;

    public NewFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_new, container, false);
        GridView gridView = view.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(DataManager.getTotalResultStructs());
        gridView.setAdapter(adapter);

        return view;
    }
}
