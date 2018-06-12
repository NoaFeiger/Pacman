package Level;
import Main.TimerListener;
import Visitors.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
    public static Visitor[][]Vmatrix;
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
    public LevelGame(JFrame frame, int level, String path_board,int points,int lives, boolean change) {
        super();
        this.frame = frame;
        this.level=level;
        turn = 0;
      //  this.main_panel = main_panel;
        tmp_array=new ArrayList<>();
        ghost_to_remove=new ArrayList<>();
        this.lifes=lives;
        this.points=points;
        this.counter=0;
        setFocusable(true);
        requestFocusInWindow();
        dofirst=true;
        if(change) {
            this.matrix = new int[32][32];
            this.Vmatrix = new Visitor[32][32];
            buildMatrix(path_board);
            collected = 0;
        }
        else{
            matrix[x][y]=0;
            matrix[startingPoint.x][startingPoint.y]=2;
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
            matrix[i][j] = line.charAt(i) - '0';
        }
    }

    boolean dofirst=true;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        counter++;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    draw(g,i,j,"block.png");
                } else if (matrix[i][j] == 2) {
                    if(dofirst)
                        startingPoint = new Point(i,j);
                    x = i;
                    y = j;
                    draw(g,i,j,pacman.getImg_pacman());
                }
                else if (matrix[i][j] == 3) {
                    if(dofirst)this.Vmatrix[i][j] = new Capsule(10,240,"capsule.png");
                    draw(g,i,j, this.Vmatrix[i][j].getPath());
                }
                else if (matrix[i][j] == 4) {
                    if(dofirst){
                        GINKEY b= new GINKEY(i,j,1);
                        this.Vmatrix[i][j]=b;
                        monsters.add(b);
                    }
                    draw(g,i,j,this.Vmatrix[i][j].getPath());
                }
                else if (matrix[i][j] == 8) {
                    if(dofirst){
                        INKY inky= new INKY(i,j,1);
                        this.Vmatrix[i][j]=inky;
                        monsters.add(inky);
                    }
                    draw(g,i,j,this.Vmatrix[i][j].getPath());
                }
                else if (matrix[i][j] == 'a'-'0') {
                    draw(g,i,j,"fire_ball.png");
                    }
                else if (matrix[i][j] == 9) {
                    draw(g,i,j,"water_bomb.png");
                }
                else if (matrix[i][j] == 5) {
                    draw(g,i,j,"water.png");
                    if(dofirst)this.Vmatrix[i][j] = new EnergyCapsule(50,4);
                }
                else if (matrix[i][j] == 6) {
                    draw(g,i,j,"pineapple.png");
                    if(dofirst)this.Vmatrix[i][j] = new PineAppleCapsule(100,4);
                }
                else if (matrix[i][j] == 7) {
                    draw(g,i,j,"apple.png");
                    if(dofirst)this.Vmatrix[i][j] = new AppleCapsule(200,4);
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
            else if(counter>=26&&level==2) {
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
            dofirst=false;
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
        if(matrix[tmp_x][tmp_y]!=1 & matrix[tmp_x][tmp_y]!='b'-'0'){//not a block
            if(!freeze) {
                matrix[x][y] = 0;
            }
            if(Vmatrix[tmp_x][tmp_y] !=null) {
                statusChange = pacman.accept(Vmatrix[tmp_x][tmp_y]);
                //statusChange = Vmatrix[tmp_x][tmp_y].visit(pacman.ac);
                Vmatrix[tmp_x][tmp_y]=null;
            }
            x = tmp_x;
            y = tmp_y;
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
            g.setColor(Color.ORANGE);
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
    } }



