package com.example.appli3voiture.Logic;


import java.util.ArrayList;

public class GameManager {
    final static int ROWS = 6;

    private int score = 0;
    private int life;
    private int meet;

   private int posCar;
    private int col;
    private int row;

    public GameManager(int life) {
        this.life = 3;
        this.score = 0;

        row = 0;
        col = 0;

        meet = 0;
        posCar = 2;
    }


    public int getMeet() {
        return meet;
    }

    public int getPosCar() {
        return posCar;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setPosCar(int posCar) {
        this.posCar = posCar;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setMeet(int meet) {
        this.meet = meet;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isMeet(int bool){ //0 man 1 heart
        if((row == ROWS - 1) && (col== posCar)){
            if(bool == 0) {
                meet++;
            }

            return true;
        }
        else return false;
    }


        public boolean isGameEnded() {
        if(life == meet)
            return true;
        return false;
    }


    }



