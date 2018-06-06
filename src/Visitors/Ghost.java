package Visitors;

import Level.LevelGame;
import Main.TimerListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Ghost implements TimerListener,Visitor{
   protected int x;
   protected int freeze;
   protected int y;
   private String img_path;
   private int speed;
   boolean alive;
   private int count;
   protected int id;
   protected directions last_direct;
   protected Visitor temp;
   protected int tempnum;
   protected int visible;

    public Ghost(int x, int y, String path_img,int speed, int id){
       this.img_path = path_img;
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

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img) {
        this.img_path = img;
    }

    @Override
    public StatusChange action() {
        StatusChange statusChange = null;
        if (!alive){
            LevelGame.Vmatrix[x][y]=null;
            LevelGame.matrix[x][y]=0;
        }
        else if(freeze>0) { // TODO check order of if else
            this.img_path = "GINKY_FROZEN.png";
            freeze--;
            if(freeze<=0)
                this.img_path = "GINKY.png";
        }
        else if (visible>0)
            visible--;
        else {
            if (this.count == this.speed) {
                this.count = 0;
                statusChange = move();
            }
        }
        this.count++;
        return statusChange;
    }

    public StatusChange move() {
        // create a list of all moves possibilities
        StatusChange statusChange = null;
        int tmp_x=x;
        int tmp_y=y;
        HashSet<Integer>set=new HashSet<>();
        set.add('b'-'0');set.add(1);set.add(4);set.add(8);
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
        if (size!=0){
        //int random = (int)(Math.random() *size );
        directions d=getCLosest(poss);
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
        if(LevelGame.matrix[x][y]==2){
            System.out.println("HIT");
            statusChange = LevelGame.getPacMan().accept(this);
        }
        LevelGame.Vmatrix[tmp_x][tmp_y]=temp;
        LevelGame.matrix[tmp_x][tmp_y]=tempnum;
        temp = LevelGame.Vmatrix[x][y];
        tempnum = LevelGame.matrix[x][y];
        LevelGame.matrix[x][y]=id; // new place of GINKEY
        LevelGame.Vmatrix[x][y]=this;
    }
        return statusChange;
    }
    private directions getCLosest(ArrayList<directions> options){
        int curX=0,curY=0;
        int pacX = LevelGame.pacManX();
        int pacY = LevelGame.pacManY();
        int curDiss = Integer.MAX_VALUE;
        directions toReturn = options.get(0);
        for(directions d: options){
            curX = x;
            curY = y;
            switch (d){
                case UP:{
                    curY--;
                    break;
                }
                case DOWN:{
                    curY++;
                    break;
                }
                case LEFT:{
                    curX--;
                    break;
                }
                case RIGHT:{
                    curX++;
                    break;
                }
            }
            if(distance(new Point(curX,curY),new Point(pacX,pacY))<curDiss){
                curDiss = distance(new Point(curX,curY),new Point(pacX,pacY));
                toReturn = d;
            }
        }
        return toReturn;
    }
    private int distance(Point p1, Point p2){
        return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y);
    }
}
enum directions{RIGHT,LEFT,UP,DOWN}

