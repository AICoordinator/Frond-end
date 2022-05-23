package com.example.frontapp.Data;

import com.example.frontapp.UserData.ResultStruct;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final DataManager instance = new DataManager();

    //결과창 정보들
    private static ResultStruct[] resultStructs;

    //저장하는지 여부
    private static boolean[] selectedImages = new boolean[5];

    //프로필 사진들 불러오기
    private static List<Images> totalResultStructs;


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

    public static boolean isSelected(int n) {
        return selectedImages[n];
    }

    public static List<Images> getTotalResultStructs() {
        return totalResultStructs;
    }

    public static void setTotalResultStructs(List<Images> totalResultStructs) {
        DataManager.totalResultStructs = totalResultStructs;
    }
}
