package com.example.frontapp.Data;

import com.example.frontapp.UserData.ResultStruct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataManager {
    private static final DataManager instance = new DataManager();

    //결과창 정보들
    private static ResultStruct[] resultStructs;

    //저장하는지 여부
    private static boolean[] selectedImages = new boolean[5];

    //프로필 사진들 불러오기
    private static List<Images> totalResultStructs;
    private static List<Images> upTotalResultStructs;
    private static List<Images> downTotalResultStructs;


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
    public static List<Images> getUpTotalResultStructs() {
        return upTotalResultStructs;
    }
    public static List<Images> getDownTotalResultStructs() {
        return downTotalResultStructs;
    }

    public static void setTotalResultStructs(List<Images> totalResultStructs) {
        DataManager.totalResultStructs = totalResultStructs;
        DataManager.upTotalResultStructs = new ArrayList<Images>();
        DataManager.downTotalResultStructs = new ArrayList<Images>();
        upTotalResultStructs.addAll(totalResultStructs);
        downTotalResultStructs.addAll(totalResultStructs);

        upTotalResultStructs.sort(new Comparator<Images>() {
            @Override
            public int compare(Images images, Images t1) {
                return images.getScore().compareTo(t1.getScore());
            }
        });
        downTotalResultStructs.sort(new Comparator<Images>() {
            @Override
            public int compare(Images images, Images t1) {
                int ret = images.getScore().compareTo(t1.getScore());
                return ret * -1;
            }
        });
    }

    public static int getTotalResultStructsSize() {
        if(totalResultStructs == null)
            return 0;
        else
            return totalResultStructs.size();
    }

}