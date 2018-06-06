package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pacman extends JFrame implements ActionListener {
    private int frame;
    private ImageIcon[] im;
    private Timer timer;
    private int x, y, dx, dy;
    public Pacman(){
        frame = 0; x = 30; y = 30;  dx = 15; dy = 15;
        im = new ImageIcon[3];
        im = new ImageIcon[3];
        im[0] = new ImageIcon("car1.gif");
        im[1] = new ImageIcon("car2.gif");
        im[2] = new ImageIcon("car3.gif");
        timer = new Timer(200,this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == timer){
            frame = (frame + 1) % 3;
            x = (x + dx) % getSize().width;
            y = (y + dy) % getSize().height;
            repaint();
        }
    }
    public void paint(Graphics g){
        super.paint(g);
        im[frame].paintIcon(this, g, x, y);      }
}
