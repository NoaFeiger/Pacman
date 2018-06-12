package Visitors;

import Level.LevelGame;
import Main.TimerListener;
import javafx.util.Pair;

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
   static ArrayList<Point> corners;
   private ArrayList<Point> lastPlaces;
   private Point corner;
   private boolean beenCorner;
   protected directions cur;
   private String normal_path;
   private String frozen_path;

    public Ghost(int x, int y, String normal_path, String frozen_path, int speed, int id){
        if(corners==null)
            corners = new ArrayList<>();
        for(int i=0;i<LevelGame.matrix.length & this.corner==null;i++)
            for(int j=0;j<LevelGame.matrix.length & this.corner==null;j++)
                if(LevelGame.matrix[i][j]==5 && !corners.contains(new Point(i,j))) {
                    this.corner = new Point(i, j);
                    corners.add(this.corner);
                    System.out.println(this.corner);
                }
       lastPlaces = new ArrayList<>();
       this.normal_path = normal_path;
       this.frozen_path = frozen_path;
       this.x=x;
       this.visible=0;
       this.freeze=0;
       this.last_direct=null;
       this.count=0;
       this.y=y;
       this.id=id;
       this.speed = speed;
       this.alive = true;
       beenCorner = false;
       this.img_path = normal_path;
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
        else if(freeze>0){ // TODO check order of if else
            this.img_path = frozen_path;
            freeze--;
            if(freeze<=0)
                this.img_path = normal_path;
            System.out.println(freeze);
        }
        else if (visible>0)
            visible--;
        else {
            if (this.count >= this.speed) {
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
        set.add('b'-'0');set.add(1);set.add(4);set.add(8);set.add(9);
        ArrayList<directions> poss=new ArrayList<>();
        if (((x)<30)&&(!set.contains(LevelGame.matrix[x + 1][y]))) // RIGHT
            poss.add(directions.RIGHT);
        if (((x>1)&&(!set.contains(LevelGame.matrix[x - 1][y])))) // LEFT
            poss.add(directions.LEFT);
        if (((y)>1)&&(!set.contains(LevelGame.matrix[x][y - 1]))) // UP
            poss.add(directions.UP);
        if (((y<30)&&(!set.contains(LevelGame.matrix[x][y + 1])))) // DOWN
            poss.add(directions.DOWN);

        int size=poss.size();
        if (size!=0){
        //int random = (int)(Math.random() *size );
            directions d = null;
        if(!beenCorner & corner!=null) {
            d = getClosest(poss, corner);
        }
        else {
            Point pacP = new Point(LevelGame.pacManX(), LevelGame.pacManY());
            d = getClosest(poss, pacP);
        }
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
            LevelGame.Vmatrix[tmp_x][tmp_y]=temp;
            LevelGame.matrix[tmp_x][tmp_y]=tempnum;
            return statusChange;
        }
        if(!beenCorner & LevelGame.matrix[x][y]==5) {
            beenCorner = true;
            System.out.println("Corner " + this.corner);
            freeze = 10;
        }
        LevelGame.Vmatrix[tmp_x][tmp_y]=temp;
        LevelGame.matrix[tmp_x][tmp_y]=tempnum;
        temp = LevelGame.Vmatrix[x][y];
        tempnum = LevelGame.matrix[x][y];
        LevelGame.matrix[x][y]=id; // new place of GINKEY
        LevelGame.Vmatrix[x][y]=this;
        updateList(x,y);
        cur = d;
        }
        return statusChange;
    }
    private directions getClosest(ArrayList<directions> options,Point des){
        int curX=0,curY=0;
        int pacX = des.x;
        int pacY = des.y;
        int curDis = Integer.MAX_VALUE;
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
            if(distance(new Point(curX,curY),new Point(pacX,pacY))<curDis){
                curDis = distance(new Point(curX,curY),new Point(pacX,pacY));
                toReturn = d;
            }
        }
        return toReturn;
    }
    private int distance(Point p1, Point p2){
        int been = 0;
        if(lastPlaces.contains(p1)) {
            been = 15;
            if (lastPlaces.lastIndexOf(p1) > 10)
                been = 25;
        }
        return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y)+been;
                //-free(p1,0);
    }
    private int free(Point p,int t){
        int free = 0;
        if(t>2)
            return 0;
        if(p.x>0 && LevelGame.matrix[x-1][y]!=1)
            free++;
        if(p.y>0 && LevelGame.matrix[x][y-1]!=1)
            free++;
        if(p.x<31 && LevelGame.matrix[x+1][y]!=1)
            free++;
        if(p.y<31 && LevelGame.matrix[x][y+1]!=1)
            free++;
        free += free(new Point(p.x+1,p.y),t+1);
        free += free(new Point(p.x,p.y+1),t+1);
        free += free(new Point(p.x-1,p.y),t+1);
        free += free(new Point(p.x,p.y+1),t+1);
        return free;
    }
    private void updateList(int u, int v){
        this.lastPlaces.add(new Point(u,v));
        if(this.lastPlaces.size()>25)
            this.lastPlaces.remove(0);
    }
}
enum directions{RIGHT,LEFT,UP,DOWN}

