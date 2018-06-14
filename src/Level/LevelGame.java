package Level;
import Main.TimerListener;
import Visitors.*;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelGame extends JPanel{
    public static ArrayList<TimerListener> monsters;
    private JFrame frame;
    private JPanel main_panel;
    private  int counter;
    public static int[][] matrix;
    public static ArrayList<Ghost> array_ghost;
    public static int[][] matrix_walls;
    public static Capsule[][] matrix_capsule;
    public static ArrayList<Capsule> speCasules;
    private static int x; // pacman place
    private static int y;
    public static int collected;
    private static Point startingPoint;
    private int points;
    private int lifes;
    public int turn;
    private final int delay = 50;
    private static Pacman pacman;
    public static ArrayList<Ghost>ghost_to_remove;
    public static ArrayList<Ghost> tmp_array;
    private  int level;
    private Color boardColor;
    private ArrayList<Pair<Point,Capsule>> capsulesPlaces;

    public LevelGame(JFrame frame, int level, String path_board,int points,int lives, boolean change, Color boardColor) {
        super();
        this.frame = frame;
        this.level=level;
        turn = 0;
        tmp_array=new ArrayList<>();
        ghost_to_remove=new ArrayList<>();
        this.lifes=lives;
        this.points=points;
        this.counter=0;
        setFocusable(true);
        requestFocusInWindow();
        dofirst=true;
        Ghost.corners = null;
        this.boardColor = boardColor;
        if(speCasules == null)
            speCasules = new ArrayList<>();
        if(change) {
            speCasules = new ArrayList<>();
            this.matrix = new int[32][32];
            this.array_ghost = new ArrayList<>();
            matrix_capsule=new Capsule[32][32];
            matrix_walls =new int[32][32];
            buildMatrix(path_board);
//            for(int[]aa:matrix) {
//                for (int aaa : aa){
//                    System.out.print(aaa);
//                }
//                System.out.println();;
//            }
            collected = 0;
        }
        else{
            matrix[x][y]=0;
            matrix[startingPoint.x][startingPoint.y]=2;
            x = startingPoint.x;
            y = startingPoint.y;
        }
        if(level==1){
        pacman = new NicePacman(x,y,lives,points);
        }
        if(level==2){
            pacman=new SafePacman(x,y,lives,points);
        }
        if(level==3){
            pacman=new AngryPacman(x,y,lives,points);
        }
        monsters = new ArrayList<>();
        turn = 0;
    }

    private void buildMatrix(String path_board) {
        BufferedReader reader = null;
        try {
            File file = new File(path_board);
            reader = new BufferedReader(new FileReader(file));
            String line;
            int j = 0;
            while ((line = reader.readLine()) != null) {
                buildLine(line, j);
                j++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void buildLine(String line, int j) {
        for (int i = 0; i < line.length(); i++) {
           int num=line.charAt(i) - '0';
            matrix[i][j] = num;
            if (num == 1) { //block
                matrix_walls[i][j]=1;
            } else if (num == 2) {
                    startingPoint = new Point(i,j);
                x = i;
                y = j;
            }
            else if (num == 3) { // regularCapsule
               matrix_capsule[i][j] = new Capsule(10,240,"capsule.png", new Point(i,j));
            }
            else if (num== 4) { // ghost ginky
                    GINKEY b= new GINKEY(i,j,1);
                    array_ghost.add(b);
                //    monsters.add(b);
                }
            else if (num == 8) { //Inky
                    INKY inky= new INKY(i,j,1);
                    this.array_ghost.add(inky);
                  //  monsters.add(inky);
            }
         //   else if (num == 'a'-'0') { // Fire Ball
           //     array_ghost[i][j]=new
            //}
           // else if (matrix[i][j] == 9) {
             //   draw(g,i,j,"water_bomb.png");
           // }
            else if (num == 5) { //energy capsule
               matrix_capsule[i][j] = new EnergyCapsule(50,4, new Point(i,j));
            }
            else if (num == 6) {
                this.matrix_capsule[i][j] = new PineAppleCapsule(100,4, new Point(i,j));
                speCasules.add(matrix_capsule[i][j]);
            }
            else if (num == 7) {
                matrix_capsule[i][j] = new AppleCapsule(200,4, new Point(i,j));
                speCasules.add(matrix_capsule[i][j]);
            }
            else if (num == 'b'-'0') { // block cage
                if (turn < 10)
                    matrix_walls[i][j]='b'-'0';
                else
                    matrix_walls[i][j] = 0;
            }
        }
    }

    boolean dofirst=true;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        counter++;
       // turn++;
        if(turn%30==0)
        swapCapsules();
        //walls
        for(int i=0;i<matrix_walls.length;i++){
            for(int j=0;j<matrix_walls[i].length;j++) {
                if (matrix_walls[i][j] == 1)
                    draw(g, i, j, "block.png");
                 if(matrix_walls[i][j] == 'b' - '0'){
                    draw(g, i, j, "temp_block.png");
                }
            }
        }

        //capsules
        for(int i=0;i<matrix_capsule.length;i++){
            for(int j=0;j<matrix_capsule[i].length;j++){
                if (matrix_capsule[i][j]!=null)
                    draw(g,i,j,matrix_capsule[i][j].getPath());
            }
        }
        //ghosts
        for(int i = 0; i< array_ghost.size(); i++){
            draw(g,array_ghost.get(i).getX_ghost(),array_ghost.get(i).getY_ghost(), array_ghost.get(i).getPath());
            }

        draw(g,x,y,pacman.getImg_pacman()); // draw pacman

        if(counter>=28&&level==1) {
            for (int i = 16; i < 20; i++) {
                draw(g, i, 12, "temp_block.png");
                matrix[i][12] = 1;
            }
        }
        else if(counter>=22&&level==2) {
            for (int i = 14; i < 18; i++) {
                draw(g, i, 9, "temp_block.png");
                matrix[i][9] = 1;
            }
        }
        else if(counter>=22&&level==3) {
            for (int i = 14; i < 18; i++) {
                draw(g, i, 9, "temp_block.png");
                matrix[i][9] = 1;
            }
        }


/*
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    draw(g,i,j,"block.png");
                } else if (matrix[i][j] == 2) {
                    if(dofirst)
                        startingPoint = new Point(i,j);
                    x_ghost = i;
                    y_ghost = j;
                    draw(g,i,j,pacman.getImg_pacman());
                }
                else if (matrix[i][j] == 3) {
                    if(dofirst)this.array_ghost[i][j] = new Capsule(10,240,"capsule.png");
                    draw(g,i,j, this.array_ghost[i][j].getPath());
                }
                else if (matrix[i][j] == 4) {
                    if(dofirst){
                        GINKEY b= new GINKEY(i,j,1);
                        this.array_ghost[i][j]=b;
                        monsters.add(b);
                    }
                    draw(g,i,j,this.array_ghost[i][j].getPath());
                }
                else if (matrix[i][j] == 8) {
                    if(dofirst){
                        INKY inky= new INKY(i,j,1);
                        this.array_ghost[i][j]=inky;
                        monsters.add(inky);
                    }
                    draw(g,i,j,this.array_ghost[i][j].getPath());
                }
                else if (matrix[i][j] == 'a'-'0') {
                    draw(g,i,j,"fire_ball.png");
                    }
                else if (matrix[i][j] == 9) {
                    draw(g,i,j,"water_bomb.png");
                }
                else if (matrix[i][j] == 5) {
                    draw(g,i,j,"water.png");
                    if(dofirst)this.array_ghost[i][j] = new EnergyCapsule(50,4);
                }
                else if (matrix[i][j] == 6) {
                    if(dofirst)this.array_ghost[i][j] = new PineAppleCapsule(100,4);
                    draw(g,i,j,this.array_ghost[i][j].getPath());
                }
                else if (matrix[i][j] == 7) {
                    if(dofirst) array_ghost[i][j] = new AppleCapsule(200,4);
                    draw(g,i,j,this.array_ghost[i][j].getPath());
                }
                else if (matrix[i][j] == 'b'-'0') {
                    if (turn < 10)
                        draw(g, i, j, "temp_block.png");
                    else
                        matrix[i][j] = 0;
                }
            }
            if(counter>=22&&level==1) {
                for (int i = 16; i < 20; i++) {
                    draw(g, i, 12, "temp_block.png");
                    matrix[i][12] = 1;
                }
            }
            else if(counter>=22&&level==2) {
                for (int i = 14; i < 18; i++) {
                    draw(g, i, 9, "temp_block.png");
                    matrix[i][9] = 1;
                }
            }
            else if(counter>=22&&level==3) {
                for (int i = 14; i < 18; i++) {
                    draw(g, i, 9, "temp_block.png");
                    matrix[i][9] = 1;
                }
            }
            dofirst=false;*/
    }
    public StatusChange move(boolean freeze,int vx, int vy){
        StatusChange statusChange = null;
        //pacman.switchM();
        int tmp_x=x;
        int tmp_y=y;
        if(!freeze) {
            tmp_x = (x + vx) % 32;
            tmp_y = (y + vy) % 32;
        }
        if(matrix_walls[tmp_x][tmp_y]!=1 & matrix_walls[tmp_x][tmp_y]!='b'-'0'){//not a block
          //  if(!freeze) {
            //    matrix[x_ghost][y_ghost] = 0;
           // }
            matrix[x][y]=0;
            x=tmp_x;
            y=tmp_y;
            for(int i=0;i<array_ghost.size();i++){
                if(array_ghost.get(i).getX_ghost()==tmp_x & array_ghost.get(i).getY_ghost()==tmp_y){
                    statusChange = pacman.accept(array_ghost.get(i));
                    return statusChange;
                }
            }
            if(matrix_capsule[tmp_x][tmp_y]!=null){
                statusChange = pacman.accept(matrix_capsule[tmp_x][tmp_y]);
                matrix_capsule[tmp_x][tmp_y]=null;
            }
           // if(array_ghost[tmp_x][tmp_y] !=null) {
             //   statusChange = pacman.accept(array_ghost[tmp_x][tmp_y]);
                //statusChange = array_ghost[tmp_x][tmp_y].visit(pacman.ac);
               // array_ghost[tmp_x][tmp_y]=null;
        }

        if(x<=-1)
            x=31;
        if(y<=-1)
            y=31;

        matrix[x][y] = 2;
        repaint();
        if(!freeze & pacman.isFrozen())
            pacman.unfreeze();
        if(statusChange!=null && statusChange.getFreezeTime()>0) {
            pacman.freeze();
        }
        return statusChange;
    }
    private void draw(Graphics g, int i, int j, String path){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException e){ }

        if(image==null) {
            g.setColor(boardColor);
            g.fillRect(i * 20, j * 20, 20, 20);
        }
        else{
          g.drawImage(image,i*20,j*20,20,20,this);
        }
    }

    public static int pacManX(){
        return x;
    }
    public static int pacManY(){
        return y;
    }
    public static Pacman getPacMan(){
        return pacman;
    }

    public void swapCapsules(){
        for(Capsule c: speCasules){
            swapCapsule(c);
        }
    }
    private void swapCapsule(Capsule c){
        Point newPoint = null;
        matrix_capsule[c.getPlace().x][c.getPlace().y] = null;
        while(newPoint == null){
            int x1 = (int)(Math.random()*31);
            int y1 = (int)(Math.random()*31);
            if(matrix_walls[x1][y1]==0)
                newPoint = new Point(x1,y1);
            }
        Capsule temp = matrix_capsule[newPoint.x][newPoint.y];
        if(temp!=null) {
            matrix_capsule[c.getPlace().x][c.getPlace().y] = temp;
            temp.setPlace(c.getPlace());
        }
        matrix_capsule[newPoint.x][newPoint.y] = c;
        c.setPlace(newPoint);
    }
//    private void initSpecialCapsules(int level){
//        switch(level){
//            case 1:
//
//        }
//    }




}



