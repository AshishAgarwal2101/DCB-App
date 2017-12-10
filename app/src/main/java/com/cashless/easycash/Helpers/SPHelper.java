package com.cashless.easycash.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Varsha on 09-12-2017.
 */

public class SPHelper {

    public static final String MY_PREFS_NAME = "EasyCash";

    public static void setSP(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSP(Context context, String key, String defaultValue){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String value = prefs.getString(key, defaultValue);
        if (value != null) {
            return  value;
        }
        return "none";
    }
    public static int getSP1(Context context, String key, int defaultValue){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        int value = prefs.getInt(key, defaultValue);
        return value;
    }
    public static void setSP1(Context context, String key, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
