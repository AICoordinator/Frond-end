package com.example.frontapp.UserData;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDataRepository {
    private static final String PREFERENCES_NAME = "UserLoginData";
    private static final String DEFAULT_STRING = null;
    private static final int DEFAULT_INT = -1;
    private User user;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setAllUserData(Context context, User data, String password)
    {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email",data.getEmail());
        editor.putString("password",password);
        editor.putString("token",data.getToken());
        editor.putString("nickname",data.getNickname());
        editor.commit();
    }

    public static String getStringData(Context context, String key)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key,DEFAULT_STRING);
    }

    public static int getIntData(Context context, String key)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getInt(key,DEFAULT_INT);
    }



}
