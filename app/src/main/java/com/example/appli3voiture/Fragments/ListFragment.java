package com.example.appli3voiture.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appli3voiture.Activities.TopTenScoreActivity;
import com.example.appli3voiture.Adapters.PlayerAdapter;
import com.example.appli3voiture.Logic.SharedPreferencesManager;
import com.example.appli3voiture.Model.Score;
import com.example.appli3voiture.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private TopTenScoreActivity topTenScoreActivity;
    private RecyclerView RV_LST_scoreList;
    private ArrayList<Score> allScores ;
    SharedPreferencesManager spm;
    private TextView listFrag_LBL_Details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        InitView();

        return view;
    }

    private void InitView() {
        allScores = new ArrayList<>();
        spm = SharedPreferencesManager.getMe();
        Gson g = new Gson();
        int numOfRecords = spm.getInt("NUMBER_OF_RECORDS", 0);

        for (int i = 0; i < numOfRecords; i++) {
            String str = spm.getString("score" + i, "");
            Score s;
            s = g.fromJson(str, Score.class);
            System.out.println("score from spm = " + s);
            allScores.add(s);
        }
        /*allScores.add(new Score(20, "odelia",3,4));
        allScores.add(new Score(30, "avy",30,48));*/

        if (numOfRecords == 0) {
            listFrag_LBL_Details.setText("not records yet");
        }
        else
            listFrag_LBL_Details.setText("touch name to see location on map");

        PlayerAdapter adapter_player = new PlayerAdapter(allScores, topTenScoreActivity.getCallbackMap() );

        RV_LST_scoreList.setLayoutManager(new LinearLayoutManager(topTenScoreActivity, LinearLayoutManager.VERTICAL,false));
        RV_LST_scoreList.setHasFixedSize(true);//taille fixe
        RV_LST_scoreList.setItemAnimator(new DefaultItemAnimator()); //scorling beau
        RV_LST_scoreList.setAdapter(adapter_player);
        System.out.println("allScores = " + allScores.toString());
    }
    public void setActivity(TopTenScoreActivity activity) {

        this.topTenScoreActivity = activity;
    }
    private void findViews(View view) {
        RV_LST_scoreList = view.findViewById(R.id.RV_LST_scoreList);
        listFrag_LBL_Details = view.findViewById(R.id.listFrag_LBL_Details);
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }
}

