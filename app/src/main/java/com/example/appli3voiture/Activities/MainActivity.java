package com.example.appli3voiture.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//import com.bumptech.glide.Glide;
import com.example.appli3voiture.Logic.GameManager;
import com.example.appli3voiture.Model.SensorMoove;
import com.example.appli3voiture.Model.SoundMeet;
import com.example.appli3voiture.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_SPEED = "KEY_SPEED";
    final int ROWS = 6;
    final int COLS = 5;
    private int speed = 0;

    private ShapeableImageView main_IMG_background;
    private ShapeableImageView main_IMG_hearts[];
    private ImageView main_IMG_1, main_IMG_2, main_IMG_3, main_IMG_4, main_IMG_5, main_IMG_6, main_IMG_7, main_IMG_8, main_IMG_9, main_IMG_10,
            main_IMG_11, main_IMG_12, main_IMG_13, main_IMG_14, main_IMG_15,main_IMG_16,main_IMG_17,main_IMG_18,main_IMG_19,main_IMG_20,
            main_IMG_21,main_IMG_22,main_IMG_23,main_IMG_24,main_IMG_25 ,main_IMG_26, main_IMG_27,main_IMG_28,main_IMG_29,main_IMG_30;

    private Button main_BTN_left;
    private ShapeableImageView main_IMG_cars[];
    private Button main_BTN_right;
    private ImageView mat[][] = new ImageView[COLS][ROWS];


    private GameManager gameManager;
    private MaterialTextView main_LBL_score;
    private Random random;
    private CountDownTimer timer;

    private String modeSelected = "0";
    private String speedSelected= "0";
    private int ManOrHeart = 0; //0 man 1 heart
    private int sensorActive =0;
    private float mobileRotation;
    private SoundMeet soundMeet;
    private int resIDsound;
    private DisplayMetrics displayMetrics;
    private SensorMoove sensorMoove;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getMenuValues();
        random = new Random();
        displayMetrics = new DisplayMetrics();
        gameManager = new GameManager(main_IMG_hearts.length);
        timerStart();


        main_BTN_right.setOnClickListener(view -> buttonActivity("right"));
        main_BTN_left.setOnClickListener(view -> buttonActivity("left"));

    }
    private void getMenuValues() {
        Intent previousIntent = getIntent();
        modeSelected = previousIntent.getExtras().getString(KEY_MODE);
        speedSelected = previousIntent.getExtras().getString(KEY_SPEED);
        UpdateKeys();
    }

    private void UpdateKeys() {
        if(speedSelected.equals("FAST"))
            speed = 500;
        else if(speedSelected.equals("SLOW"))
            speed = 1000;
        if(modeSelected.equals("SNS")){
           setSensor();

        }

        if(modeSelected.equals("BTN")){
            main_BTN_left.setVisibility(View.VISIBLE);
            main_BTN_right.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
          }


    private void timerStart() {
            timer = new CountDownTimer(1000000,speed) {
                @Override
                public void onTick(long l) {
                    goOnUI();
                }
                @Override
                public void onFinish() {
                    timer.cancel();
                    changeActivity("YOU WON!!", gameManager.getScore());

                }
            };
            timer.start();
    }
    public void goOnUI(){
        //bottom - en bas
        if(gameManager.getRow() == ROWS-1){

            mat[gameManager.getRow()][gameManager.getCol()].setImageResource(0);
            gameManager.setRow(0);
            gameManager.setCol(random.nextInt(COLS));
            ManOrHeart = random.nextInt(2);
        }

        isGameEndedUI();

        mat[gameManager.getRow()][gameManager.getCol()].setImageResource(0);
        gameManager.setRow(gameManager.getRow()+1);
        if(ManOrHeart == 0)
            mat[gameManager.getRow()][gameManager.getCol()].setImageResource(R.drawable.man);
        else
            mat[gameManager.getRow()][gameManager.getCol()].setImageResource(R.drawable.heart);

        isGameLooseUI();
        main_LBL_score.setText(gameManager.getScore() + "");
        gameManager.setScore(gameManager.getScore()+1);
        main_LBL_score.setText(String.valueOf(gameManager.getScore()));


    }
    public void isGameEndedUI(){
        //game over
        if(gameManager.isGameEnded()){
            toastAndVibrate("Touched");
            timer.cancel();
            changeActivity("GAME OVER", gameManager.getScore());

        }
    }
    public void isGameLooseUI(){
        //loose
        if(gameManager.isMeet(ManOrHeart)) {

            if(ManOrHeart == 0) {
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getMeet()].setVisibility(View.INVISIBLE);
                toastAndVibrate("Touched");

                soundPlay("fail");
            }
            else if(ManOrHeart == 1) {
                toastAndVibrate("GOOD JOB");
                soundPlay("succes");

                if(gameManager.getMeet() <(main_IMG_hearts.length) && gameManager.getMeet() >0) {
                    gameManager.setMeet(gameManager.getMeet()-1);
                        for (int i = 0; i < main_IMG_hearts.length- gameManager.getMeet(); i++)
                            main_IMG_hearts[i].setVisibility(View.VISIBLE);

                }

            }
        }
    }

    public void soundPlay(String theme) {
        if(theme == "fail"){
            soundMeet = new SoundMeet(this, R.raw.fail);
        }
        else if(theme == "succes"){
            soundMeet = new SoundMeet(this, R.raw.succsess);

        }
        soundMeet.playSound();
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }
    private void toast(String text) {
        Toast.makeText(this,text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void changeActivity(String status, int score) {
       Intent scoreIntent = new Intent(this, ScoreActivity.class);
        //Intent scoreIntent = new Intent(this, TopTenScoreActivity.class);
        scoreIntent.putExtra(ScoreActivity.KEY_STATUS, status);
        scoreIntent.putExtra(ScoreActivity.KEY_SCORE, score);
        startActivity(scoreIntent);
        finish();
    }
    private void buttonActivity(String answere) {
       if(answere == "right"){
           if(gameManager.getPosCar() != (COLS - 1)){
               main_IMG_cars[gameManager.getPosCar()].setImageResource(0);
               main_IMG_cars[gameManager.getPosCar()  +1].setImageResource(R.drawable.car);
               gameManager.setPosCar(gameManager.getPosCar()+1);
               isGameLooseUI();
               isGameEndedUI();
           }

       }
       if(answere == "left") {

           if (gameManager.getPosCar()  != 0) {
               main_IMG_cars[gameManager.getPosCar()].setImageResource(0);
               main_IMG_cars[gameManager.getPosCar() - 1].setImageResource(R.drawable.car);
               gameManager.setPosCar(gameManager.getPosCar()-1);
               isGameLooseUI();
               isGameEndedUI();
           }
       }
   }

    private void findViews() {
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_LBL_score= findViewById(R.id.main_LBL_score);
        main_IMG_background = findViewById(R.id.main_IMG_background);

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_IMG_cars = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_car1),
                findViewById(R.id.main_IMG_car2),
                findViewById(R.id.main_IMG_car3),
                findViewById(R.id.main_IMG_car4),
                findViewById(R.id.main_IMG_car5)
        };
        main_IMG_1 = findViewById(R.id.main_IMG_1);
        main_IMG_2 = findViewById(R.id.main_IMG_2);
        main_IMG_3 = findViewById(R.id.main_IMG_3);
        main_IMG_4 = findViewById(R.id.main_IMG_4);
        main_IMG_5 = findViewById(R.id.main_IMG_5);
        main_IMG_6 = findViewById(R.id.main_IMG_6);
        main_IMG_7 = findViewById(R.id.main_IMG_7);
        main_IMG_8 = findViewById(R.id.main_IMG_8);
        main_IMG_9 = findViewById(R.id.main_IMG_9);
        main_IMG_10 = findViewById(R.id.main_IMG_10);
        main_IMG_11 = findViewById(R.id.main_IMG_11);
        main_IMG_12 = findViewById(R.id.main_IMG_12);
        main_IMG_13 = findViewById(R.id.main_IMG_13);
        main_IMG_14 = findViewById(R.id.main_IMG_14);
        main_IMG_15 = findViewById(R.id.main_IMG_15);
        main_IMG_16 = findViewById(R.id.main_IMG_16);
        main_IMG_17 = findViewById(R.id.main_IMG_17);
        main_IMG_18 = findViewById(R.id.main_IMG_18);
        main_IMG_19 = findViewById(R.id.main_IMG_19);
        main_IMG_20 = findViewById(R.id.main_IMG_20);
        main_IMG_21 = findViewById(R.id.main_IMG_21);
        main_IMG_22 = findViewById(R.id.main_IMG_22);
        main_IMG_23 = findViewById(R.id.main_IMG_23);
        main_IMG_24 = findViewById(R.id.main_IMG_24);
        main_IMG_25 = findViewById(R.id.main_IMG_25);
        main_IMG_26 = findViewById(R.id.main_IMG_26);
        main_IMG_27 = findViewById(R.id.main_IMG_27);
        main_IMG_28 = findViewById(R.id.main_IMG_28);
        main_IMG_29 = findViewById(R.id.main_IMG_29);
        main_IMG_30 = findViewById(R.id.main_IMG_30);

        ImageView t[][] = {{main_IMG_1, main_IMG_2, main_IMG_3,main_IMG_4, main_IMG_5},
                {main_IMG_6,main_IMG_7, main_IMG_8, main_IMG_9,main_IMG_10},
                {main_IMG_11, main_IMG_12,main_IMG_13, main_IMG_14, main_IMG_15},
                {main_IMG_16,main_IMG_17,main_IMG_18,main_IMG_19,main_IMG_20},
                {main_IMG_21,main_IMG_22,main_IMG_23,main_IMG_24,main_IMG_25},
                {main_IMG_26, main_IMG_27,main_IMG_28,main_IMG_29,main_IMG_30}};
        mat = t;

    }


    private void setSensor() {
        SensorMoove.CallBackSteps callBack_steps = new SensorMoove.CallBackSteps() {
            @Override
            public void moveLeft() {
                buttonActivity("left");
            }

            @Override
            public void moveRight() {
                buttonActivity("right");
            }

        };
        SensorMoove sensorMoove = new SensorMoove(this, callBack_steps);
        sensorMoove.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    }



