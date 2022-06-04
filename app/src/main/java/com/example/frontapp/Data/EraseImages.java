package com.example.frontapp.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EraseImages {
    @SerializedName("title")
    private List<String> title;

    public EraseImages() {
        title = new ArrayList<String>();
    }

    public void add(String str) {
        this.title.add(str);
    }

}
