package Visitors;

import java.awt.*;

public class AppleCapsule extends Capsule {
    int shown;
    public AppleCapsule(int points, int quantity, Point place) {
        super(points, quantity, "apple.png", place);
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
