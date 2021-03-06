package Visitors;

import Level.LevelGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BLINKY extends Ghost implements Visitor {

    public boolean isBlinky_can_shoot() {
        return blinky_can_shoot;
    }

    public void setBlinky_can_shoot(boolean blinky_can_shoot) {
        this.blinky_can_shoot = blinky_can_shoot;
    }

    private boolean blinky_can_shoot;
    public BLINKY(int x, int y, int speed) {

        super(x, y, "BLINKY.png","BLINKY.png", speed, 9);
        this.blinky_can_shoot=true;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(0, 0, 0);
    }

    @Override
    public StatusChange visit(SafePacman nice_p) {
        return new StatusChange(0, 0, 0);
    }

    @Override
    public StatusChange visit(AngryPacman angryPacman_p) { //kills pacman
        angryPacman_p.setLives(angryPacman_p.getLives() - 1);

        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange move() {
        StatusChange statusChange = super.move();
            //todo Add switch of d
            if(blinky_can_shoot) {
               // FireBall fire_bomb = new FireBall(this, x_ghost, y_ghost, "fire_ball.png", 1, 'a' - '0', d);//TODO check if it goes on the right direction and check id

                blinky_can_shoot = false;
                LevelGame.tmp_array.add(new FireBall(x_ghost,y_ghost,"fire_ball.png","Snowflake.png",1,'a'-'0',this));
            }
         // if(LevelGame.matrix[x_ghost][y_ghost]==2){
         //   System.out.println("HIT");
           // statusChange = LevelGame.getPacMan().accept(this);
        //}
        return statusChange;
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}
