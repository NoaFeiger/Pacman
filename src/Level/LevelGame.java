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
    public static int[][] matrix;
    public static Visitor[][]Vmatrix;
    private int x; // pacman place
    private int y;
    protected int vx;
    protected int vy;
    private int desiredX;
    private int desiredY;
    private int points;
    private int lifes;
    private final int delay = 10;
    private Pacman pacman;
    public static ArrayList<Ghost>ghost_to_remove;
    public static ArrayList<Ghost> tmp_array;
    public LevelGame(JFrame frame, int level, String path_board,int points,int lives) {
        super();
        this.frame = frame;
      //  this.main_panel = main_panel;
        tmp_array=new ArrayList<>();
        ghost_to_remove=new ArrayList<>();
        this.matrix = new int[32][32];
        this.Vmatrix = new Visitor[32][32];
        this.lifes=lives;
        this.points=points;
        vx = 0;
        vy = 0;
        desiredX = 0;
        desiredY = 0;
        dofirst=true;
        buildMatrix(path_board);
        if(level==1){
        pacman = new NicePacman(x,y,lives,points);
        }
        if(level==2){
            pacman=new SafePacman(x,y,lives,points);
        }
        if(level==3){
            pacman=new AngryPacman(x,y,lives,points);
        }
        addKeyListener(KeyEvent.VK_UP, 0, -1);
        addKeyListener(KeyEvent.VK_DOWN, 0, 1);
        addKeyListener(KeyEvent.VK_LEFT, -1, 0);
        addKeyListener(KeyEvent.VK_RIGHT, 1, 0);
        monsters = new ArrayList<>();
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
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        int countC = 188;
//        while(countC<240)
//        for(int i=0;i<matrix.length;i++)
//            for(int j=0;j<matrix.length;j++){
//                if(countC<240 & matrix[i][j]==0 & matrix[31-i][j]!=2 & Math.random()<0.4){
//                    matrix[i][j]=3;
//                    matrix[31-i][j]=3;
//                    countC+=2;
//                }
//            }
//            for(int i=0;i<matrix.length;i++){
//                for(int j=0;j<matrix.length;j++)
//                    System.out.print(matrix[j][i]);
//                System.out.println();
//            }
    }

    private void buildLine(String line, int j) {
        for (int i = 0; i < line.length(); i++) {
            matrix[i][j] = line.charAt(i) - '0';
        }
    }

    private void addKeyListener(int keyEvent, int mvx, int mvy) {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyEvent, 0), "forward" + keyEvent);
        this.getActionMap().put("forward" + keyEvent, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desiredX = mvx;
                desiredY = mvy;
            }
        });
    }
    boolean dofirst=true;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // ImageIcon img=new ImageIcon("pacman.jpg");
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    draw(g,i,j,"block.png");
                } else if (matrix[i][j] == 2) {
                    x = i;
                    y = j;
                    if(pacman.isOpenMouth())
                        draw(g,i,j,"pacman_open.png");
                    else{
                        draw(g,i,j,"pacman_close.png");
                    }
                } else if (matrix[i][j] == 3) {
                    draw(g,i,j, "capsule.png");
                    if(dofirst)this.Vmatrix[i][j] = new Capsule(10,240,"temp");
                } else if (matrix[i][j] == 4) {
                    draw(g,i,j,"GINKY.png");
                    if(dofirst){
                        GINKEY b= new GINKEY(i,j,6);
                        this.Vmatrix[i][j]=b;
                        monsters.add(b);
                    }
                }
                else if (matrix[i][j] == 8) {
                    draw(g,i,j,"INKY.png");
                    if(dofirst){
                        INKY inky= new INKY(i,j,3);
                        this.Vmatrix[i][j]=inky;
                        monsters.add(inky);
                    }
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


            }
            dofirst=false;
    }
    public StatusChange move(boolean freeze){


        StatusChange statusChange = null;
        pacman.switchM();
        if(!freeze)
            tryDesired();
        int tmp_x=x;
        int tmp_y=y;
        if(!freeze) {
            tmp_x = (x + vx) % 32;
            tmp_y = (y + vy) % 32;
        }
        if(matrix[tmp_x][tmp_y]!=1){//not a block
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
    private void tryDesired(){
        int tmp_x = (x + desiredX) % 32;
        int tmp_y = (y + desiredY) % 32;

        if(matrix[tmp_x][tmp_y]!=1){
            vx = desiredX;
            vy = desiredY;
        }
    }
}



