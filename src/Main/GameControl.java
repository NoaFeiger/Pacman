package Main;

import Level.LevelGame;
import Visitors.StatusChange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControl implements ActionListener, KeyListener {
    JFrame frame;
    private LevelGame levelGame;
    private int lifes;
    private boolean change_level;
    private int points;
    private int level;
    private int freeze;
    private Timer timer;
    private int delay = 50;
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
        this.statusP = new JPanel(new GridLayout(4, 1));
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
        this.frame.addKeyListener(this);
        JButton speed_up = new JButton("Speed up");
        speed_up.setSize(100,50);
        this.statusP.add(speed_up);
        speed_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(delay == 50) {
                    delay = 10;
                }
                else {
                    delay = 50;
                }
                speed_up.setText("delay " + delay);
                timer.setDelay(delay);
            }
        });
        ImageIcon pause_img=resizeImage("pause.jpg",40,40);
        JButton pause=new JButton(pause_img);
        pause.setOpaque(false);
        this.statusP.add(pause);
        pause.addActionListener(new ActionListener() {
            boolean wait=true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wait) // pause
                {
                    timer.stop();
                    wait=false;
                }
                else{ // play
                    timer.start();
                    wait=true;
                }
            }
        });
    }

    public void startGame() {
        this.level = 1;
        this.levelGame = new LevelGame(frame, this.level, "BoardLevel" + this.level, 0, 3,true);
        this.levelGame.setFocusable(true);
        this.levelGame.setFocusTraversalKeysEnabled(false);
        this.levelGame.requestFocusInWindow();
        this.levelGame.addKeyListener(this);
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
        this.levelGame = new LevelGame(frame, this.level, "BoardLevel" + this.level, this.points, this.lifes,change);
        this.levelGame.addKeyListener(this);
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

            this.levelGame.requestFocus(false);
            levelGame.turn++;
            LevelGame.getPacMan().switchM();
            int curLife = this.lifes;
            StatusChange statusChange = null;
            for (TimerListener t : LevelGame.monsters) {
                statusChange = t.action();
                update(statusChange);
            }
            LevelGame.monsters.addAll(LevelGame.tmp_array);
            LevelGame.tmp_array.clear();
            if (LevelGame.ghost_to_remove != null) {
                for (int i = 0; i < LevelGame.ghost_to_remove.size(); i++) {
                    LevelGame.monsters.remove(LevelGame.ghost_to_remove.get(i));
                }
            }
            if (this.freeze <= 0) {
                freeze = 0;
            } else {
                freeze--;
            }
            if(this.lifes==curLife)
                update(statusChange);
            this.levelGame.repaint();
        }
    }

    private void update(StatusChange statusChange) {
        if (statusChange != null) {
            this.points = this.points + statusChange.getPoints();
            this.freeze += statusChange.getFreezeTime();
            if(statusChange.getFreezeTime()>0)
                LevelGame.getPacMan().freeze();
            else// =\<
                LevelGame.getPacMan().unfreeze();
            if (statusChange.getLifes()!=0) {
                this.lifes = this.lifes + statusChange.getLifes();
                System.out.println("Life");
                if (this.lifes != 0) {
                    timer.stop();
                    nextLevel(false);
                }
            }
            updateStatus();
        }
    }
    private void updateStatus(){
        if (this.lifes == 0) {
            endScene();
        }
        if (LevelGame.collected>10) { //TODO CHANGE POINTS
            if(this.level<3) {
                System.out.println("Next Level");
                timer.stop();
                nextLevel(true);
            }
            else{
                endScene();
            }
        }
        pointsL.setText("Points: " + this.points);
        lifesL.setText("Lifes: " + this.lifes);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       // System.out.println("asdfghjkl");
        StatusChange statusChange = null;
        int key = e.getKeyCode();
        if(freeze>0)
            return;
        switch (key) {
            case KeyEvent.VK_RIGHT:
                statusChange = this.levelGame.move(freeze>0,1,0);
                break;
            case KeyEvent.VK_DOWN:
                statusChange = this.levelGame.move(freeze>0,0,1);
                break;
            case KeyEvent.VK_LEFT:
                statusChange = this.levelGame.move(freeze>0,-1,0);
                break;
            case KeyEvent.VK_UP:
                statusChange = this.levelGame.move(freeze>0,0,-1);
                break;
        }
        update(statusChange);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void endScene(){
        timer.stop();
        LastPage last_page = new LastPage(frame, main_panel, this.points);
        frame.remove(this.levelGame); // move to the game page
        frame.add(last_page);
        frame.repaint();
        frame.revalidate();
    }
    public ImageIcon resizeImage(String path, int i, int i1) {
        ImageIcon undo_image = new ImageIcon(path);
        Image image = undo_image.getImage(); // transform it
        Image new_img = image.getScaledInstance(i, i1,  Image.SCALE_SMOOTH); // scale it the smooth way
        undo_image = new ImageIcon(new_img);  // transform it back
        return undo_image;
    }
}