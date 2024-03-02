package com.example.appli3voiture.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appli3voiture.Activities.TopTenScoreActivity;
import com.example.appli3voiture.Fragments.MapFragment;
import com.example.appli3voiture.Interfaces.CallbackMap;
import com.example.appli3voiture.Model.Score;
import com.example.appli3voiture.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Score> allScores = new ArrayList<>();
    private int[] numbers_image = new int[10] ;

    private CallbackMap callbackMap;

    public PlayerAdapter(ArrayList<Score> allScores, CallbackMap  callbackMap ) {

        this.callbackMap = callbackMap;
        this.allScores = allScores;

        numbers_image[0] = R.drawable.one;
        numbers_image[1] = R.drawable.two;
        numbers_image[2] = R.drawable.three;
        numbers_image[3] = R.drawable.four;
        numbers_image[4] = R.drawable.five;
        numbers_image[5] = R.drawable.six;
        numbers_image[6] = R.drawable.seven;
        numbers_image[7] = R.drawable.eight;
        numbers_image[8] = R.drawable.nine;
        numbers_image[9] = R.drawable.ten ;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreline, parent, false );
        return new ScoreViewHolder(view);
    }

//quand je scrole je ne vois que le debut apres ca monte
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Score score = getItem(position);
        if(score == null) {
            return;
        }
        ScoreViewHolder playerViewHolder = (ScoreViewHolder) holder;
        playerViewHolder.setPlayer(score);
        playerViewHolder.setImage(numbers_image[position]);
        playerViewHolder.ScoreLine_LBL_name.setText(score.getName());
        playerViewHolder.ScoreLine_LBL_score.setText(""+score.getScore());
    }
    private Score getItem(int position) {
        return allScores.get(position);
    }
    @Override
    public int getItemCount() {
        return allScores.size();
    }
    private class ScoreViewHolder extends RecyclerView.ViewHolder{

        private AppCompatImageView ScoreLine_IMG_imageLine;
        private MaterialTextView ScoreLine_LBL_name;
        private MaterialTextView ScoreLine_LBL_score;
        private Score score ;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ScoreLine_IMG_imageLine = itemView.findViewById(R.id.ScoreLine_IMG_imageLine);
            this.ScoreLine_LBL_name = itemView.findViewById(R.id.ScoreLine_LBL_name);
            this.ScoreLine_LBL_score = itemView.findViewById(R.id.ScoreLine_LBL_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbackMap.changeLocation(score.getLon(), score.getLat());
                }
            });
        }
        public void setPlayer(Score s) {
            this.score = s ;
        }

        public void setImage(int l) {
            this.ScoreLine_IMG_imageLine.setImageResource(l);
        }
    }


}
