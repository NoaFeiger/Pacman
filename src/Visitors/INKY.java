package Visitors;

import Level.LevelGame;

import java.util.ArrayList;
import java.util.HashSet;


public class INKY extends Ghost implements Visitor {
private int counter;
    public INKY(int x, int y,int speed) {
        super(x, y, "INKY.jpg",speed,8);
        counter=0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { // pacman lose 10 points and freeze for 3 sec
        return new StatusChange(-10,0,3);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // inky freeze for 5 sec
        this.freeze=5;
        return new StatusChange(0,0,0);
    }
    public void freeze(int sec){
        this.freeze=sec;
    }
    @Override
    public void move() {
        // create a list of all moves possibilities
        int tmp_x=x;
        int tmp_y=y;
            HashSet<Integer> set = new HashSet<>();
            set.add(1);
            set.add(4);
            set.add(8);
            ArrayList<directions> poss = new ArrayList<>();
        if (((x)<30)&&(!set.contains(LevelGame.matrix[x + 1][y]))) // RIGHT
            poss.add(directions.RIGHT);
        if (((x>1)&&(!set.contains(LevelGame.matrix[x - 1][y])))) // LEFT
            poss.add(directions.LEFT);
        if (((y)>1)&&(!set.contains(LevelGame.matrix[x][y - 1]))) // UP
            poss.add(directions.UP);
        if (((y<30)&&(!set.contains(LevelGame.matrix[x][y + 1])))) // DOWN
            poss.add(directions.DOWN);

        if(poss.size()>1) {
            if (last_direct != null) {
                switch (last_direct) { // remove the option to go the opposite way of the last move
                    case RIGHT: {
                        poss.remove(directions.LEFT);
                        break;
                    }
                    case LEFT: {
                        poss.remove(directions.RIGHT);
                        break;
                    }
                    case DOWN: {
                        poss.remove(directions.UP);
                        break;
                    }
                    case UP: {
                        poss.remove(directions.DOWN);
                        break;
                    }
                }
            }
        }
        int size=poss.size();
        if (size!=0){
            int random = (int)(Math.random() *size );
            directions d=poss.get(random);
            last_direct=d;

            switch (d){
                case UP:{
                    y--;
                    break;
                }
                case DOWN:{
                    y++;
                    break;
                }
                case LEFT:{
                    x--;
                    break;
                }
                case RIGHT:{
                    x++;
                    break;
                }
            }
            LevelGame.Vmatrix[tmp_x][tmp_y]=temp; // save the icon before
            LevelGame.matrix[tmp_x][tmp_y]=tempnum;
            temp = LevelGame.Vmatrix[x][y];
            tempnum = LevelGame.matrix[x][y];
            LevelGame.matrix[x][y]=id; // new place of GINKEY
            LevelGame.Vmatrix[x][y]=LevelGame.Vmatrix[tmp_x][tmp_y];

            if(counter++%4==0) {
                WaterBomb water_bomb;
                switch (d) {
                    case UP: {
                        if (LevelGame.matrix[x][y - 1] != 1) {
                            water_bomb = new WaterBomb(this, x, y - 1, "water_bomb.jpg", 2, 9, d);//TODO check if it goes on the right direction
                            water_bomb.move();

                            LevelGame.tmp_array.add(water_bomb);
                        }
                        break;
                    }
                    case DOWN: {
                        if (LevelGame.matrix[x][y + 1] != 1) {
                            water_bomb = new WaterBomb(this, x, y + 1, "water_bomb.jpg", 2, 9, d);//TODO check if it goes on the right direction
                            water_bomb.move();

                            LevelGame.tmp_array.add(water_bomb);
                        }
                        break;
                    }
                    case LEFT: {
                        if (LevelGame.matrix[x - 1][y] != 1) {
                            water_bomb = new WaterBomb(this, x - 1, y, "water_bomb.jpg", 2, 9, d);//TODO check if it goes on the right direction
                            water_bomb.move();
                            LevelGame.tmp_array.add(water_bomb);
                        }
                        break;
                    }
                    default: { //RIGHT
                        if (LevelGame.matrix[x + 1][y] != 1) {
                            water_bomb = new WaterBomb(this, x + 1, y, "water_bomb.jpg", 2, 9, d);//TODO check if it goes on the right direction
                            water_bomb.move();
                            LevelGame.tmp_array.add(water_bomb);
                        }
                        break;
                    }
                }
            }
            }
        }
    }






