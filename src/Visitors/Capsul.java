package Visitors;

import javax.swing.*;

public class Capsul {
    private int points;
    private int quantity;
    private ImageIcon img_capsul;
    public Capsul(int points,int quantity, String path){
        this.points=points;
        this.quantity=quantity;
        this.img_capsul=new ImageIcon(path);
    }
}
