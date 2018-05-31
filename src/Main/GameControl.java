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
    private JLabel pointsL;
    private JLabel lifesL;
    private JPanel statusP;
    private Color curBackground;

    public GameControl(JFrame frame){
        this.frame = frame;
        this.lifes = 3;
        this.points = 0;
        this.level = 0;
        this.freeze = 0;
        this.curBackground = new Color(3,3,99);
        this.statusP = new JPanel(new GridLayout(2,1));
        this.pointsL = new JLabel("Points: 0",SwingConstants.CENTER);
        this.pointsL.setForeground(Color.WHITE);
        this.pointsL.setFont(new Font("",Font.BOLD,20));
        this.pointsL.setSize(200,200);
        this.pointsL.setHorizontalAlignment(0);
        this.statusP.add(pointsL);
        this.lifesL = new JLabel("Lifes: 0",SwingConstants.CENTER);
        this.lifesL.setForeground(Color.WHITE);
        this.lifesL.setFont(new Font("",Font.BOLD,20));
        this.statusP.add(lifesL);
        this.statusP.setBackground(this.curBackground);
        this.lifesL.setSize(100,200);
        this.lifesL.setHorizontalAlignment(0);
        this.frame.add(statusP,BorderLayout.EAST);
        this.frame.getContentPane().setBackground(this.curBackground);
    }
    public void startGame(){
        this.level = 1;
        this.levelGame = new LevelGame(frame,this.level,"BoardLevel" + this.level);
        this.levelGame.setBackground(this.curBackground);
        frame.add(this.levelGame);
        frame.setBackground(Color.BLACK);
        frame.repaint();
        frame.revalidate();
        timer = new Timer(delay, this);
        timer.start();
    }
    public void nextLevel(){
        this.level++;
        frame.remove(this.levelGame);
        this.levelGame = new LevelGame(frame,this.level,"BoardLevel" + this.level);
        this.curBackground = new Color(5,5,160);
        this.statusP.setBackground(this.curBackground);
        this.levelGame.setBackground(curBackground);
        this.frame.getContentPane().setBackground(this.curBackground);
        frame.add(this.levelGame);
        frame.repaint();
        frame.revalidate();
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer) {
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
            pointsL.setText("Points: " + this.points);
            lifesL.setText("Lifes: " + this.lifes);
        }
    }
}