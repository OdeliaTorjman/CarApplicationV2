package com.example.appli3voiture.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.appli3voiture.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MenueActivity extends AppCompatActivity {
    private ShapeableImageView open_IMG_background;
    private ShapeableImageView Open_IMG_man;
    private SwitchCompat open_SWT_fast;
    private SwitchCompat open_SWT_sensors;

    private MaterialButton open_BTN_StartGame;
    private MaterialButton open_BTN_TopTen;
    private TextView open_TXT_Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);
        findViews();
        open_BTN_StartGame.setOnClickListener(view -> StartGame());
        open_BTN_TopTen.setOnClickListener(view -> OpenTopScore());


    }

    private void OpenTopScore() {
        startActivity(new Intent(this, TopTenScoreActivity.class));
        finish();
    }

    private void StartGame() {
        Intent intent = new Intent(this, MainActivity.class);

        if(open_SWT_fast.isChecked()){
            //faire le jeux FAST
            intent.putExtra(MainActivity.KEY_SPEED, "FAST");

        }
        else intent.putExtra(MainActivity.KEY_SPEED, "SLOW");


        if(open_SWT_sensors.isChecked() ){
            //activer les sonsors
            intent.putExtra(MainActivity.KEY_MODE, "SNS");
        }
        else intent.putExtra(MainActivity.KEY_MODE, "BTN");

        startActivity(intent);
        finish();
    }

    private void findViews() {
        open_IMG_background = findViewById(R.id.open_IMG_background);
        Open_IMG_man = findViewById(R.id.open_IMG_man);
        open_BTN_StartGame = findViewById(R.id.open_BTN_StartGame);
        open_BTN_TopTen = findViewById(R.id.open_BTN_TopTen);
        open_TXT_Title = findViewById(R.id.open_TXT_Title);
        open_SWT_fast = (SwitchCompat) findViewById(R.id.open_SWT_fast);
        open_SWT_sensors = findViewById(R.id.open_SWT_sensors);

    }


}