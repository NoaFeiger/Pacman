package Visitors;

import javax.swing.*;

public class Capsule {
    private int points;
    private int quantity;
    private ImageIcon img_capsul;
    public Capsule(int points,int quantity, String path){
        this.points=points;
        this.quantity=quantity;
        this.img_capsul=new ImageIcon(path);
    }
}
