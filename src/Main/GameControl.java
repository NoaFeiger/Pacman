package Main;

import Level.LevelGame;
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
    private Timer timer;
    private final int delay = 200;

    public GameControl(JFrame frame){
        this.frame = frame;
        this.lifes = 3;
        this.points = 0;
        this.level = 0;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer)
            this.levelGame.move();
    }
}