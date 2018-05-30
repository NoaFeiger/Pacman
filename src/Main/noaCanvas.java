package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class noaCanvas extends JFrame implements ActionListener {
    int[][] pacmatrix=new int[10][10];

    private Timer timer;
    private int x, y;
    private final int delay = 200;
    public noaCanvas(){
        for(int i=1;i<9;i++)
            for(int j=1;j<9;j++)
                pacmatrix[i][j]=1;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        x = 0; y = 0;
        timer = new Timer(delay,this);
       // timer.start();
        setVisible(true);
        setSize(500,500);
    }
    public void paint(Graphics g){
        super.paint(g);
       ImageIcon img=new ImageIcon("pacman.jpg");
       for(int i=0;i<10;i++)
           for(int j=0;j<10;j++)
           {
               if(pacmatrix[i][j]==0)
               {
                   g.setColor(Color.BLACK);
                   g.drawRect(i*50,j*50,20,20);

               }
               else
               {
                   g.setColor(Color.blue);
                   g.drawRect(i*50,j*50,20,20);
               }
           }
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == timer){
            x = x + 5;  y = y + 5;
            repaint();
        }
    }
}
