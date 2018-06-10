package Visitors;

import Level.LevelGame;

import java.util.ArrayList;
import java.util.HashSet;


public class INKY extends Ghost implements Visitor {
private int counter;
    public INKY(int x, int y,int speed) {
        super(x, y, "INKY.png","GINKY_FROZEN.png",speed,8);
        counter=0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { // pacman lose 10 points and freeze for 3 sec
        return new StatusChange(-10,0,10);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // inky freeze for 5 sec
        this.freeze=10;
        return new StatusChange(0,0,0);
    }

    @Override
    public String getPath() {
        return super.getImg_path();
    }

    public void freeze(int sec){
        this.freeze=sec;
    }
    int often=15;
    @Override
    public StatusChange move() {
        StatusChange statusChange = super.move();


        if (counter++ % often == often-1) {
            WaterBomb water_bomb;
            often=8;
            switch (cur) {
                case UP: {
                    if (LevelGame.matrix[x][y - 1] != 1) {
                        water_bomb = new WaterBomb(this, x, y - 1, "water_bomb.jpg", 2, 9, cur);//TODO check if it goes on the right direction
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        LevelGame.tmp_array.add(water_bomb);
                    }
                    break;
                }
                case DOWN: {
                    if (LevelGame.matrix[x][y + 1] != 1) {
                        water_bomb = new WaterBomb(this, x, y + 1, "water_bomb.jpg", 2, 9, cur);//TODO check if it goes on the right direction
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        LevelGame.tmp_array.add(water_bomb);
                    }
                    break;
                }
                case LEFT: {
                    if (LevelGame.matrix[x - 1][y] != 1) {
                        water_bomb = new WaterBomb(this, x - 1, y, "water_bomb.jpg", 2, 9, cur);//TODO check if it goes on the right direction
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        LevelGame.tmp_array.add(water_bomb);
                    }
                    break;
                }
                default: { //RIGHT
                    if (LevelGame.matrix[x + 1][y] != 1) {
                        water_bomb = new WaterBomb(this, x + 1, y, "water_bomb.jpg", 2, 9, cur);//TODO check if it goes on the right direction
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        LevelGame.tmp_array.add(water_bomb);
                    }
                    break;
                }
            }
        }
        return statusChange;
    }
    }






