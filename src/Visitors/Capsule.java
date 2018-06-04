package Visitors;

import javax.swing.*;

public class Capsule implements Visitor{

    private int points;
    private int quantity;
    private String img_capsule;

    public Capsule(int points,int quantity, String path){
        this.points=points;
        this.quantity=quantity;
        this.img_capsule = path;
    }

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

    public String getImg_capsule() {
        return img_capsule;
    }

    public void setImg_capsule(String img_capsule) {
        this.img_capsule = img_capsule;
    }
    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(this.points,0,0);
    }

    @Override
    public StatusChange visit(SafePacman nice_p) {
        return new StatusChange(this.points,0,0);
    }

    @Override
    public StatusChange visit(AngryPacman nice_p) {
        return new StatusChange(this.points,0,0);
    }

    @Override
    public String getPath() {
        return this.img_capsule;
    }
}
