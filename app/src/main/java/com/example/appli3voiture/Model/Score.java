package com.example.appli3voiture.Model;

import androidx.annotation.NonNull;

public class Score {

    private int score;
    private String name;
    private double lon;
    private double lat;


    public Score() {
        score=0;
        name = "";
        this.lon= 0;
        this.lat = 0;
    }
    public Score(int score, String name, double lon, double lat) {
        this.score=score;
        this.name = name;
        this.lon= lon;
        this.lat = lat;

    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
