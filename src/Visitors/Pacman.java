package Visitors;

import javax.swing.*;

public abstract class Pacman implements Visited{
    private int x;
    private int y;
    private String curPic;
    boolean openMouth;
    boolean frozen;
    private String openMouthP;
    private String closeMouthP;

    public Pacman(int x,int y,String openPath, String closePath, int lives,int score){
        this.x=x;
        this.y=y;
        this.curPic = openPath;
        this.openMouthP = openPath;
        this.closeMouthP = closePath;
        this.lives=lives;
        this.score=score;
        openMouth = true;
        frozen = false;
        switchM();
    }
    public void switchM(){
        openMouth = !openMouth;
        if(!frozen)
        if(openMouth)
            curPic = this.openMouthP;
        else
            curPic = this.closeMouthP;
    }
    public void freeze(){
        this.frozen = true;
        this.curPic = "pacman_frozen.png";
    }
    public void unfreeze(){
        frozen = false;
        this.curPic = this.openMouthP;
    }
    public boolean isOpenMouth() {
        return openMouth;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getImg_pacman() {
        return curPic;
    }

    public void setImg_pacman(String img_pacman) {
        this.curPic = img_pacman;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isFrozen(){
        return frozen;
    }

    private int lives;
    private int score;

}
