package com.example.frontapp.UserData;

public class DataManager {
    private static final DataManager instance = new DataManager();
    private static ResultStruct[] resultStructs;

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
}
