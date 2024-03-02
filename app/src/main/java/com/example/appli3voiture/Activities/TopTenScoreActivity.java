package com.example.appli3voiture.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appli3voiture.Fragments.ListFragment;
import com.example.appli3voiture.Fragments.MapFragment;
import com.example.appli3voiture.Interfaces.CallbackMap;
import com.example.appli3voiture.Logic.SharedPreferencesManager;
import com.example.appli3voiture.Model.Score;
import com.example.appli3voiture.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class TopTenScoreActivity extends AppCompatActivity {
    private ListFragment list_fragment;
    private MapFragment map_fragment;
    private ShapeableImageView topTen_IMG_background;

    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_score_activity);

        map_fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.TopTen_FRM_map, map_fragment).commit();

        list_fragment = new ListFragment();
        list_fragment.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.TopTen_FRM_list, list_fragment).commit();

    }


    public CallbackMap getCallbackMap() {
        return map_fragment.getCallbackMap();
    }

    private void findViews() {
        topTen_IMG_background = findViewById(R.id.topTen_IMG_background);

    }
    }
