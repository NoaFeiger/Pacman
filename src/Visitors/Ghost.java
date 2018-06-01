package Visitors;

import Level.LevelGame;
import Main.TimerListener;

import javax.swing.*;
import java.util.ArrayList;

public class Ghost implements TimerListener{
   protected int x;
   protected int y;
   private ImageIcon img;
   private int speed;
   boolean alive;
   private int count;
   protected int id;
   private directions last_direct;
   private Visitor temp;
   private int tempnum;

    public Ghost(int x, int y, String path_img,int speed, int id){
       this.img=new ImageIcon(path_img);
       this.x=x;
       this.last_direct=directions.RIGHT; //TODO change
       this.count=0;
       this.y=y;
       this.id=id;
       this.speed = speed;
       this.alive = true;
   }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }


    @Override
    public void action() {
        if(this.count == this.speed){
            this.count = 0;
            move();
        }
        this.count++;

    }

    private void move() {
        // create a list of all moves possibilities
        int tmp_x=x;
        int tmp_y=y;
        ArrayList<directions> poss=new ArrayList<>();
        if(LevelGame.matrix[x+1][y]!=1) // RIGHT
            poss.add(directions.RIGHT);
        if(LevelGame.matrix[x-1][y]!=1) // LEFT
            poss.add(directions.LEFT);
        if(LevelGame.matrix[x][y-1]!=1) // UP
            poss.add(directions.UP);
        if(LevelGame.matrix[x][y+1]!=1) // DOWN
            poss.add(directions.DOWN);
        if(poss.size()>1) {

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
        int size=poss.size();
        int random = (int)(Math.random() *size );
        directions d=poss.get(random);
        last_direct=d;
      //  Visitor last_temp = temp;
       // int last_temp_num = tempnum;

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
        temp = LevelGame.Vmatrix[x][y];
        tempnum = LevelGame.matrix[x][y];

        //[lasttemp]-G->[temp]
        //TODO
        LevelGame.matrix[x][y]=id; // new place of GINKEY
        LevelGame.Vmatrix[x][y]=LevelGame.Vmatrix[tmp_x][tmp_y];
        LevelGame.Vmatrix[tmp_x][tmp_y]=temp;
        LevelGame.matrix[tmp_x][tmp_y]=tempnum;
    }

}
enum directions{RIGHT,LEFT,UP,DOWN}

