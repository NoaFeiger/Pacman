package Visitors;

import Level.LevelGame;
import Main.TimerListener;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Ghost implements TimerListener,Visitor{
   protected int x_ghost;
   protected int freeze;
   protected int y_ghost;
   private String img_path;
   private int speed;
   boolean alive;
   private int count;
   protected int id;
   protected directions last_direct;
   protected Ghost temp;
   protected int tempnum;
   protected int visible;
   public static ArrayList<Point> corners;
   private ArrayList<Point> lastPlaces;
   private Point corner;
   protected boolean beenCorner;
   protected directions cur;
   private String normal_path;
   private String frozen_path;
   private int startX;
   private int startY;

    public Ghost(int x, int y, String normal_path, String frozen_path, int speed, int id){
        if(corners==null)
            corners = new ArrayList<>();
        this.corner = null;
        beenCorner = true;
        for(int i=0;i<LevelGame.matrix.length & this.corner==null;i++)
            for(int j=0;j<LevelGame.matrix.length & this.corner==null;j++)
                if(this.corner==null && LevelGame.matrix[i][j] == 5 && !corners.contains(new Point(i,j))) {
                    this.corner = new Point(i, j);
                    corners.add(this.corner);
                    System.out.println(this.corner);
                }
        if(this.corner==null & corners.size()>1)
            this.corner = corners.get(1);
       lastPlaces = new ArrayList<>();
       this.normal_path = normal_path;
       this.frozen_path = frozen_path;
       this.x_ghost =x;
       this.visible=0;
       this.freeze=0;
       this.last_direct=null;
       this.count=0;
       this.y_ghost =y;
       this.id=id;
       this.speed = speed;
       this.alive = true;
       beenCorner = false;
       this.img_path = normal_path;
       this.startX = x;
       this.startY = y;
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

    public int getX_ghost() {
        return x_ghost;
    }

    public void setX_ghost(int x_ghost) {
        this.x_ghost = x_ghost;
    }

    public int getY_ghost() {
        return y_ghost;
    }

    public void setY_ghost(int y_ghost) {
        this.y_ghost = y_ghost;
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
            LevelGame.array_ghost.remove(this);
           // LevelGame.matrix[x_ghost][y_ghost]=0;
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
        int tmp_x=x_ghost;
        int tmp_y= y_ghost;
        HashSet<Integer>set=new HashSet<>();
        set.add('b'-'0');set.add(1);
        ArrayList<directions> poss=new ArrayList<>();
        if (((x_ghost)<30)&&(!set.contains(LevelGame.matrix_walls[x_ghost + 1][y_ghost]))) // RIGHT
            poss.add(directions.RIGHT);
        if (((x_ghost >1)&&(!set.contains(LevelGame.matrix_walls[x_ghost - 1][y_ghost])))) // LEFT
            poss.add(directions.LEFT);
        if (((y_ghost)>1)&&(!set.contains(LevelGame.matrix_walls[x_ghost][y_ghost - 1]))) // UP
            poss.add(directions.UP);
        if (((y_ghost <30)&&(!set.contains(LevelGame.matrix_walls[x_ghost][y_ghost + 1])))) // DOWN
            poss.add(directions.DOWN);

        int size=poss.size();
        if (size!=0){
        //int random = (int)(Math.random() *size );
            directions d;
        if(!beenCorner & corner!=null) {
            d = getClosest(poss, corner);
            //System.out.println("To Corner " + corner);
        }
        else {
            Point pacP = new Point(LevelGame.pacManX(), LevelGame.pacManY());
            d = getClosest(poss, pacP);
            System.out.println("To PacMan " + pacP);
            if(corners!=null)
            for(Point ppp:corners)
                System.out.println(ppp);
            System.out.println("-----");
        }
        last_direct=d;

        switch (d){
            case UP:{
                y_ghost--;
                break;
            }
            case DOWN:{
                y_ghost++;
                break;
            }
            case LEFT:{
                x_ghost--;
                break;
            }
            case RIGHT:{
                x_ghost++;
                break;
            }
        }
        if(x_ghost ==LevelGame.pacManX()&& y_ghost ==LevelGame.pacManY()) {
            System.out.println("HIT");
            statusChange = LevelGame.getPacMan().accept(this);
            //LevelGame.array_ghost[tmp_x][tmp_y] = temp;
            LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            cur = d;
            beenCorner = false;
            return statusChange;
        }
        if(!beenCorner & LevelGame.matrix[x_ghost][y_ghost]==5) {
            beenCorner = true;
            System.out.println("Corner " + this.corner);
            freeze = 10;
        }
       // LevelGame.array_ghost[tmp_x][tmp_y]=temp
        // LevelGame.matrix[tmp_x][tmp_y]=tempnum;
       // temp = LevelGame.array_ghost[x_ghost][y_ghost];
        //tempnum = LevelGame.matrix[x_ghost][y_ghost];
     //   LevelGame.array_ghost[x_ghost][y_ghost]=id; //
       // LevelGame.array_ghost[x_ghost][y_ghost]=this;//new place of GINKEY
       // x_ghost=tmp_x;//new x of GINKEY
        //y_ghost=tmp_y;//new y of GINKEY
        updateList(x_ghost, y_ghost);
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
            curX = x_ghost;
            curY = y_ghost;
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
            been = lastPlaces.lastIndexOf(p1)*3;
        }
        return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y)+been;
    }

    private void updateList(int u, int v){
        this.lastPlaces.add(new Point(u,v));
        if(this.lastPlaces.size()>25)
            this.lastPlaces.remove(0);
    }
    public void setBeenCorner(boolean beenCorner){
        this.beenCorner = beenCorner;
    }
    public void startOver(){
        this.x_ghost = this.startX;
        this.y_ghost = this.startY;
    }
}
enum directions{RIGHT,LEFT,UP,DOWN}

