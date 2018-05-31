package Main;

import Level.LevelGame;
import Visitors.StatusChange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControl implements ActionListener {
    JFrame frame;
    private LevelGame levelGame;
    private int lifes;
    private int points;
    private int level;
    private int freeze;
    private Timer timer;
    private final int delay = 200;

    public GameControl(JFrame frame){
        this.frame = frame;
        this.lifes = 3;
        this.points = 0;
        this.level = 0;
        this.freeze = 0;
    }
    public void startGame(){
        this.level = 1;
        this.levelGame = new LevelGame(frame,this.level,"BoardLevel" + this.level);
        frame.add(this.levelGame);
        frame.repaint();
        frame.revalidate();
        timer = new Timer(delay, this);
        timer.start();
    }
    public void nextLevel(){
        this.level++;
        frame.remove(this.levelGame);
        this.levelGame = new LevelGame(frame,this.level,"BoardLevel" + this.level);
        frame.add(this.levelGame);
        frame.repaint();
        frame.revalidate();
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer) {
            System.out.println(this.points);
            StatusChange statusChange = null;
            if(this.freeze==0)
                statusChange = this.levelGame.move();
            else
                this.freeze--;
            if (statusChange!=null){
                this.points = this.points+statusChange.getPoints();
                this.lifes = this.lifes+statusChange.getLifes();
                this.freeze = statusChange.getFreezeTime();
            }
            if(this.lifes==0) {
                System.out.println("LOSE!");
                timer.stop();
            }
            if(this.points>=500 & this.level<2){
                System.out.println("Next Level");
                timer.stop();
                nextLevel();
            }
        }
    }
}