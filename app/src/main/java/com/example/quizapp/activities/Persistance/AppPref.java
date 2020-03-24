package com.example.quizapp.activities.Persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quizapp.activities.MainActivity;
import com.example.quizapp.activities.ThirdActivity;
import com.example.quizapp.activities.Utils.UserNameAndScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AppPref {

    private static AppPref singleTonInstance = null;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Context context;
    private static Gson gson;
    private String resp;
    private static final String PREF_NAME = "takeQuiz_AppPref";
    private static final int PRIVATE_MODE = 0;

    private static final String key = "keyName";

    public void print()
    {
        System.out.println(getData().get(0));
    }
    public AppPref(Context context) {
        super();
        this.context = context;
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void storeData(ArrayList<UserNameAndScore> objectArray) {
        editor.putString(key, gson.toJson(objectArray));
        editor.commit();
        editor.apply();
    }

    public ArrayList<UserNameAndScore> getData() {
        resp = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<UserNameAndScore>>() {
        }.getType();

        if (gson.fromJson(resp, type) == null) {
            return null;
        } else {
            System.out.println(gson.fromJson(resp, type));
            return gson.fromJson(resp, type);
        }
    }

    public void clearAppPref() {

        editor.remove(key);

        editor.commit();
        editor.apply();
    }
}