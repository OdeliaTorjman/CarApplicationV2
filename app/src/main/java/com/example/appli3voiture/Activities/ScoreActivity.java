package com.example.appli3voiture.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.appli3voiture.Fragments.ListFragment;
import com.example.appli3voiture.Logic.SharedPreferencesManager;
import com.example.appli3voiture.Model.Score;
import com.example.appli3voiture.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

import android.location.Location;


public class ScoreActivity extends AppCompatActivity {
    public static final int LOCATION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";


    private MaterialTextView score_LBL_score;
    private EditText score_TextEdit_scoreNameInput;
    private Button score_BTN_submit;
    private int score;
    SharedPreferencesManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent previousScreen = getIntent();
        askToEnableGps();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        findViews();
        spm = SharedPreferencesManager.getMe();
        score_BTN_submit.setOnClickListener(view -> submitName());

        String status = previousScreen.getStringExtra(KEY_STATUS);
        score = previousScreen.getIntExtra(KEY_SCORE, 0);

        refreshUI(status, score);
    }

    private void submitName() {
        setLocationAndAddToFile();
    }

    private void askToEnableGps() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

    public void saveRecordToFile(Score newScore) {
        int numOfRecords = spm.getInt("NUMBER_OF_RECORDS", 0);
        Gson g = new Gson();
        ArrayList<Score> allScore = new ArrayList<>();

        for (int i = 0; i < numOfRecords; i++) {
            String str = spm.getString("score" + i, "");
            Score score;
            score = g.fromJson(str, Score.class);
            allScore.add(score);
            Log.d("", "addScore saveRecordToFile for ");
        }

        Comparator<Score> comparator = (s1, s2) -> {
            if (s1 == null || s2 == null)
                return 0;
            return s2.getScore() - s1.getScore();
        };

        if (numOfRecords > 0) {
            allScore.sort(comparator);
            if (numOfRecords < 10) {
                allScore.add(newScore);
                allScore.sort(comparator);
                numOfRecords++;

            } else {
                if (allScore.get(9).getScore() > newScore.getScore())
                    return;
                allScore.add(newScore);
                allScore.sort(comparator);
                allScore.remove(10);
            }

            for (int i = 0; i < numOfRecords; i++) {
                spm.putString("score" + i, g.toJson(allScore.get(i)));
            }
        } else {
            allScore.add(newScore);
            spm.putString("score" + 0, g.toJson(allScore.get(0)));
            numOfRecords++;
        }

        spm.putInt("NUMBER_OF_RECORDS", numOfRecords);
    }


    private void setLocationAndAddToFile() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // CHECK IF USER GAVE PERMISSION
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Score newScore  ;
                        if (location != null) {
                            newScore = new Score(
                                    score,
                                    getPlayerName(),
                                    location.getLongitude(),
                                    location.getLatitude()

                            );


                        } else{
                            newScore = new Score(
                                    score,
                                    getPlayerName(),
                                    34.853195,
                                    32.321457
                            );
                        }
                        saveRecordToFile(newScore);
                        changeActivity();
                    }

                });
    }


    private void changeActivity() {
        startActivity(new Intent(this, TopTenScoreActivity.class));
        finish();
    }

    private void refreshUI(String status, int score) {
        score_LBL_score.setText(status + "\n" + score);
    }

    private void findViews() {
        score_LBL_score = findViewById(R.id.score_LBL_score);
        score_TextEdit_scoreNameInput = findViewById(R.id.score_TextEdit_scoreNameInput);
        score_BTN_submit = findViewById(R.id.score_BTN_submit);
    }

    private String getPlayerName() {
        String str = score_TextEdit_scoreNameInput.getText().toString();
        if (str.isEmpty())
            return "player";
        else
            return str;
    }
}