package Visitors;

import javax.swing.*;

public class Capsul implements Visitor{
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ImageIcon getImg_capsul() {
        return img_capsul;
    }

    public void setImg_capsul(ImageIcon img_capsul) {
        this.img_capsul = img_capsul;
    }

    private int points;
    private int quantity;
    private ImageIcon img_capsul;
    public Capsul(int points,int quantity, String path){
        this.points=points;
        this.quantity=quantity;
        this.img_capsul=new ImageIcon(path);
    }

    @Override
    public void visit(NicePacman nice_p) {

    }

    @Override
    public void visit(SafePacman nice_p) {

    }

    @Override
    public void visit(AngryPacman nice_p) {

    }
}
