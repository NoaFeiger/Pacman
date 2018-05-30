package Visitors;

import javax.swing.*;

public class Capsule extends ImageIcon {
    private String type;
    private int pointsWorth;
    static int [] amounts;
    public Capsule(String type){
        super("src/"+type+".png");
        amounts = new int[3];
        switch(type){
            case "Regular":
                initRegular();
                break;
            case "Energy":

        }
    }
    private void initRegular(){
        amounts[0]=240;
        amounts[1]=240;
        amounts[2]=240;
        pointsWorth = 10;
    }
}
