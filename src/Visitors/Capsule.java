package Visitors;

import Level.LevelGame;

import javax.swing.*;
import java.awt.*;

public class Capsule implements Visitor{

    private int points;
    private int quantity;
    private String img_capsule;
    private Point place;

    public Capsule(int points,int quantity, String path, Point place){
        this.points=points;
        this.quantity=quantity;
        this.img_capsule = path;
        this.place = place;
    }
    public Point getPlace(){
        return this.place;
    }
    public void setPlace(Point p){
        this.place = p;
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
        LevelGame.speCasules.remove(this);
        LevelGame.collected++;
        return new StatusChange(this.points,0,0);
    }

    @Override
    public StatusChange visit(SafePacman nice_p) {
        LevelGame.speCasules.remove(this);
        LevelGame.collected++;
        return new StatusChange(this.points,0,0);
    }

    @Override
    public StatusChange visit(AngryPacman nice_p) {
        LevelGame.speCasules.remove(this);
        LevelGame.collected++;
        return new StatusChange(this.points,0,0);
    }

    @Override
    public String getPath() {
        return this.img_capsule;
    }
}
