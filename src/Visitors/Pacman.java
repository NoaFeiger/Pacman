package Visitors;

import javax.swing.*;

public abstract class Pacman implements Visited{
    private int x;
    private int y;
    private ImageIcon img_pacman;
    boolean openMouth;

    public Pacman(int x,int y,String path, int lives,int score){
        this.x=x;
        this.y=y;
        this.img_pacman=new ImageIcon(path);
        this.lives=lives;
        this.score=score;
        openMouth = true;
    }
    public void switchM(){
        openMouth = !openMouth;
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

    public ImageIcon getImg_pacman() {
        return img_pacman;
    }

    public void setImg_pacman(ImageIcon img_pacman) {
        this.img_pacman = img_pacman;
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

    private int lives;
    private int score;


}
