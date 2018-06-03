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
    private boolean change_level;
    private int points;
    private int level;
    private int freeze;
    private Timer timer;
    private final int delay = 200;
    private JLabel pointsL;
    private JLabel lifesL;
    private JPanel statusP;
    private Color curBackground;
    private JPanel main_panel;

    public GameControl(JFrame frame, JPanel main_panel) {
        this.frame = frame;
        this.lifes = 3;
        this.change_level = true;
        this.points = 0;
        this.main_panel = main_panel;
        this.level = 0;
        this.freeze = 0;
        this.curBackground = new Color(3, 3, 99);
        this.statusP = new JPanel(new GridLayout(2, 1));
        this.pointsL = new JLabel("Points: 0", SwingConstants.CENTER);
        this.pointsL.setForeground(Color.WHITE);
        this.pointsL.setFont(new Font("", Font.BOLD, 20));
        this.pointsL.setSize(200, 200);
        this.pointsL.setHorizontalAlignment(0);
        this.statusP.add(pointsL);
        this.lifesL = new JLabel("Lifes: 0", SwingConstants.CENTER);
        this.lifesL.setForeground(Color.WHITE);
        this.lifesL.setFont(new Font("", Font.BOLD, 20));
        this.statusP.add(lifesL);
        this.statusP.setBackground(this.curBackground);
        this.lifesL.setSize(100, 200);
        this.lifesL.setHorizontalAlignment(0);
        this.frame.add(statusP, BorderLayout.EAST);
        this.frame.getContentPane().setBackground(this.curBackground);
    }

    public void startGame() {
        this.level = 1;
        this.levelGame = new LevelGame(frame, this.level, "BoardLevel" + this.level);
        this.levelGame.setBackground(this.curBackground);
        frame.add(this.levelGame);
        frame.setBackground(Color.BLACK);
        frame.repaint();
        frame.revalidate();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void nextLevel(boolean change) {
        if (change == true)
            this.level++;
        frame.remove(this.levelGame);
        this.levelGame = new LevelGame(frame, this.level, "BoardLevel" + this.level);
        this.curBackground = new Color(5, 5, 160);
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
        if (e.getSource() == timer) {
            StatusChange statusChange = null;
            for (TimerListener t : LevelGame.monsters) {
                t.action();
            }
            LevelGame.monsters.addAll(LevelGame.tmp_array);
            LevelGame.tmp_array.clear();
            if (LevelGame.ghost_to_remove!=null){
                for(int i=0;i<LevelGame.ghost_to_remove.size();i++)
                {
                    LevelGame.monsters.remove(LevelGame.ghost_to_remove.get(i));
                }
            }
            if (this.freeze == 0)
                statusChange = this.levelGame.move();
            else
                this.freeze--;
            if (statusChange != null) {
                this.points = this.points + statusChange.getPoints();
                this.freeze = statusChange.getFreezeTime();
                if ((statusChange.getLifes() + this.lifes) != this.lifes) {
                    this.lifes = this.lifes + statusChange.getLifes();
                    if (this.lifes != 0) {
                        timer.stop();
                        nextLevel(false);
                    }
                }
                if (this.lifes == 0) {
                    timer.stop();
                    LastPage last_page = new LastPage(frame, main_panel, this.points);
                    frame.remove(this.levelGame); // move to the game page
                    frame.add(last_page);
                    frame.repaint();
                    frame.revalidate();
                }
                if (this.points >= 500 & this.level < 2) {
                    System.out.println("Next Level");
                    timer.stop();
                    nextLevel(true);
                }
                pointsL.setText("Points: " + this.points);
                lifesL.setText("Lifes: " + this.lifes);
            }
        }
    }
}