package com.example.appli3voiture;

import android.app.Application;

import com.example.appli3voiture.Logic.SharedPreferencesManager;
import com.google.android.material.imageview.ShapeableImageView;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
    }
}
