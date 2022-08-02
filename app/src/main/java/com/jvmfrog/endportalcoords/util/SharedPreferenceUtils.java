package com.jvmfrog.endportalcoords.util;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPreferenceUtils {

    // create function to save boolean value in shared preference
    public static void saveBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // create function to load boolean value from shared preference
    public static boolean loadBoolean(Context context, String name, String key) {
        SharedPreferences prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }
}
