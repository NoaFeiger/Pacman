package Visitors;

import Level.LevelGame;
import Main.Main;
import Main.TimerListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class GINKEY extends Ghost implements Visitor {

    private int visible;
    private Visitor temp;
    private int tempnum;

    public GINKEY(int x, int y, int speed) {
        super(x, y, "GINKY.jpg", 6, 4);
        this.visible = 0;
        System.out.println("GINKY CREATED");
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //kill pacman
        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //ginkey disappear for 5 sec
        this.visible = 5;
        return new StatusChange(0, 0, 0);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ginkey dies for 5 sec
        this.alive = false;
        return new StatusChange(0, 0, 0);
    }

}
