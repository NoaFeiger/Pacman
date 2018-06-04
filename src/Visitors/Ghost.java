package Visitors;

import Level.LevelGame;
import Main.TimerListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Ghost implements TimerListener{
   protected int x;
   protected int freeze;
   protected int y;
   private ImageIcon img;
   private int speed;
   boolean alive;
   private int count;
   protected int id;
   protected directions last_direct;
   protected Visitor temp;
   protected int tempnum;
   protected int visible;

    public Ghost(int x, int y, String path_img,int speed, int id){
       this.img=new ImageIcon(path_img);
       this.x=x;
       this.visible=0;
       this.freeze=0;
       this.last_direct=null;
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
        if (!alive)
        {
            LevelGame.Vmatrix[x][y]=null;
            LevelGame.matrix[x][y]=0;
        }
        else if(freeze>0) // TODO check order of if else
            freeze--;
        else if (visible>0)
            visible--;
        else {
            if (this.count == this.speed) {
                this.count = 0;
                move();
            }
        }
        this.count++;

    }

    public void move() {
        // create a list of all moves possibilities
        int tmp_x=x;
        int tmp_y=y;
        HashSet<Integer>set=new HashSet<>();
        set.add(1);set.add(4);set.add(8);
        ArrayList<directions> poss=new ArrayList<>();
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
        if (size!=0)
        {
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
        LevelGame.Vmatrix[tmp_x][tmp_y]=temp;
        LevelGame.matrix[tmp_x][tmp_y]=tempnum;
        temp = LevelGame.Vmatrix[x][y];
        tempnum = LevelGame.matrix[x][y];
        LevelGame.matrix[x][y]=id; // new place of GINKEY
        LevelGame.Vmatrix[x][y]=LevelGame.Vmatrix[tmp_x][tmp_y];
    }
    }


}
enum directions{RIGHT,LEFT,UP,DOWN}

