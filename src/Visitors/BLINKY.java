package Visitors;

import Level.LevelGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BLINKY extends Ghost implements Visitor {
    public boolean isCan_shoot_fire() {
        return can_shoot_fire;
    }

    public void setCan_shoot_fire(boolean can_shoot_fire) {
        this.can_shoot_fire = can_shoot_fire;
    }

    private boolean can_shoot_fire;
    public BLINKY(int x, int y, int speed) {
        super(x, y, "BLINKY.png","BLINKY.png", speed, 9);
        this.can_shoot_fire=true;
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
        System.out.println("PacMan dies");
        angryPacman_p.setLives(angryPacman_p.getLives() - 1);

        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange move() {
        StatusChange statusChange = null;
        // create a list of all moves possibilities
        int tmp_x = x;
        int tmp_y = y;
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(4);
        set.add(8);
        set.add('b'-'0');

        ArrayList<directions> poss = new ArrayList<>();
        if (((x)<30)&&(!set.contains(LevelGame.matrix[x + 1][y]))) // RIGHT
            poss.add(directions.RIGHT);
        if (((x>1)&&(!set.contains(LevelGame.matrix[x - 1][y])))) // LEFT
            poss.add(directions.LEFT);
        if (((y)>1)&&(!set.contains(LevelGame.matrix[x][y - 1]))) // UP
            poss.add(directions.UP);
        if (((y<30)&&(!set.contains(LevelGame.matrix[x][y + 1])))) // DOWN
            poss.add(directions.DOWN);

        if (poss.size() > 1) {
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
        int size = poss.size();
        if (size != 0) {
            Random rnd = new Random(1);
            rnd.nextInt(size);
            int random = rnd.nextInt(size);
            directions d = poss.get(random);
            last_direct = d;

            switch (d) {
                case UP: {
                    y--;
                    break;
                }
                case DOWN: {
                    y++;
                    break;
                }
                case LEFT: {
                    x--;
                    break;
                }
                case RIGHT: {
                    x++;
                    break;
                }
            }
            temp = LevelGame.Vmatrix[x][y];
            tempnum = LevelGame.matrix[x][y];
            LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
            LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            LevelGame.matrix[x][y] = id; // new place of GINKEY
            LevelGame.Vmatrix[x][y] = this;

            if(FireBall.can_shoot) {
                FireBall fire_bomb = new FireBall(this, x, y, "fire_ball.png", 1, 'a' - '0', d);//TODO check if it goes on the right direction and check id
                LevelGame.tmp_array.add(fire_bomb);
                this.can_shoot_fire=false;
            }
        }
        if(LevelGame.matrix[x][y]==2){
            System.out.println("HIT");
            statusChange = LevelGame.getPacMan().accept(this);
        }
        return statusChange;
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}