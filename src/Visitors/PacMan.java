package Visitors;

import javax.swing.*;

public class PacMan extends ImageIcon implements Visitor{
    private double speed;
    private int lifes;
    private String currDirection;
    private String wantedDirection;
    private String type;
    private int points;

    public PacMan(){
        super("src/pacman.png");
        this.type = "nice";
    }
    @Override
    public void impact(Visitor visitor) {

    }
}
