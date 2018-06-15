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
        StatusChange statusChange = null;
        // create a list of all moves possibilities
        int tmp_x = x_ghost;
        int tmp_y = y_ghost;
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(4);
        set.add(8);
        set.add('b'-'0');

        ArrayList<directions> poss = new ArrayList<>();
        if (((x_ghost)<30)&&(!set.contains(LevelGame.matrix[x_ghost + 1][y_ghost]))) // RIGHT
            poss.add(directions.RIGHT);
        if (((x_ghost >1)&&(!set.contains(LevelGame.matrix[x_ghost - 1][y_ghost])))) // LEFT
            poss.add(directions.LEFT);
        if (((y_ghost)>1)&&(!set.contains(LevelGame.matrix[x_ghost][y_ghost - 1]))) // UP
            poss.add(directions.UP);
        if (((y_ghost <30)&&(!set.contains(LevelGame.matrix[x_ghost][y_ghost + 1])))) // DOWN
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
                    y_ghost--;
                    break;
                }
                case DOWN: {
                    y_ghost++;
                    break;
                }
                case LEFT: {
                    x_ghost--;
                    break;
                }
                case RIGHT: {
                    x_ghost++;
                    break;
                }
            }
        /*
            temp = LevelGame.array_ghost[x_ghost][y_ghost];
            tempnum = LevelGame.matrix[x_ghost][y_ghost];
            LevelGame.array_ghost[tmp_x][tmp_y] = temp;
            LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            LevelGame.matrix[x_ghost][y_ghost] = id; // new place of GINKEY
            LevelGame.array_ghost[x_ghost][y_ghost] = this;
*/
            //todo Add switch of d
            if(blinky_can_shoot) {
               // FireBall fire_bomb = new FireBall(this, x_ghost, y_ghost, "fire_ball.png", 1, 'a' - '0', d);//TODO check if it goes on the right direction and check id

                blinky_can_shoot = false;
                LevelGame.tmp_array.add(new FireBall(x_ghost,y_ghost,"fire_ball.png","Snowflake.png",1,'a'-'0',this));
            }
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
