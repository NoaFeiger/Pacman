package Visitors;

import java.util.Random;

public class StatusChange {
    private int points;
    private int lifes;
    private int freezeTime;

    public StatusChange(int points,int lifes,int freezeTime){
        this.points = points;
        this.lifes = lifes;
        this.freezeTime = freezeTime;
    }
    public int getPoints(){
        return this.points;
    }
    public int getLifes(){
        return this.lifes;
    }
    public int getFreezeTime(){
        return this.freezeTime;
    }

    public String toString(){
        Random rnd = new Random(1);
        return "points: " + this.points + " lifes: " + this.lifes +" freeze: " + this.freezeTime +"   " + rnd.nextInt(10);
    }
}
