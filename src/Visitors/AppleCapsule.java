package Visitors;

public class AppleCapsule extends Capsule {
    int shown;
    public AppleCapsule(int points, int quantity) {
        super(points, quantity, "apple.png");
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
