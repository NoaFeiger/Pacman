package Visitors;

import java.awt.*;

public class PineAppleCapsule extends Capsule {
    int shown;
    public PineAppleCapsule(int points, int quantity, Point place) {
        super(points, quantity, "pineapple.png", place);
        shown = 0;
    }
    @Override
    public String getPath() {
        flash();
        if(shown>0)
            return super.getPath();
        else
            return"";
    }
    private void flash(){
        shown = (shown + 1)%4;
    }
}
