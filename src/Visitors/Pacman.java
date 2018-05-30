package Visitors;

import javax.swing.*;

public class Pacman {
    private int x;
    private int y;
    private ImageIcon img_pacman;
    private int lives;
    private int score;

    public Pacman(int x,int y,String path, int lives,int score){
    this.x=x;
    this.y=y;
    this.img_pacman=new ImageIcon(path);
    this.lives=lives;
    this.score=score;
    }
}
