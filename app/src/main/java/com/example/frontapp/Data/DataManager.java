package com.example.frontapp.Data;

import com.example.frontapp.UserData.ResultStruct;

public class DataManager {
    private static final DataManager instance = new DataManager();
    private static ResultStruct[] resultStructs;
    private static boolean[] selectedImages = new boolean[5];
    private DataManager() {}

    public static DataManager getInstance() {
        return instance;
    }

    public static ResultStruct[] getResultStructs() {
        return resultStructs;
    }

    public static void setResultStructs(ResultStruct[] resultStructs) {
        DataManager.resultStructs = resultStructs;
    }

    public static boolean select(int n) {
        selectedImages[n] = (selectedImages[n]) ? false : true;
        return selectedImages[n];
    }
}
