package com.example.appli3voiture.Logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appli3voiture.App;

public class SharedPreferencesManager {
    private static final String DB_FILE = "DB_FILE";
    private static SharedPreferencesManager spm;
    private SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if (spm == null) {
            spm = new SharedPreferencesManager(context);
        }
    }
    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }


    public static SharedPreferencesManager getMe() {
        return spm;
    }



    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public int getInt(String KEY, int defValue) {
        return sharedPreferences.getInt(KEY, defValue);
    }

    public void putInt(String KEY, int value) {
        sharedPreferences.edit().putInt(KEY, value).apply();
    }

    public String getString(String KEY, String defValue) {
        return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {
        sharedPreferences.edit().putString(KEY, value).apply();
    }


}
