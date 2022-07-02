package com.jvmfrog.endportalcoords.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.adapter.Model;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferenceUtils {

    public static void saveModelArrayList(Context context, String name, String key, ArrayList<Model> list) {
        SharedPreferences prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public static void loadModelArrayList(Context context, String name, String key, ArrayList<Model> list) {
        SharedPreferences prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Model>>() {
        }.getType();
        list = gson.fromJson(json, type);

        if (list == null) {
            list = new ArrayList<>();
        }
    }
}
