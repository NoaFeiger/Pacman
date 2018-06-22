package Visitors;

public class LOVELY extends Ghost implements Visitor {
    private int curNum;
    public LOVELY(int x, int y, int speed) {
        super(x, y, "GHOSTY/GHOSTY1.png", "GINKEY_FROZEN.png", speed, 4);
        curNum = 0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //Adding life to the pacman
        this.alive = false;
        return new StatusChange(-100, 1, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //Adding life to the pacman, freezing him and then dying.
        this.alive = false;
        return new StatusChange(-100, 1, 10);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ghost dies
        this.alive = false;
        return new StatusChange(0, -1, 5);
    }
    @Override
    public String getPath() {
        curNum = (curNum%5)+1;
        if(this.freeze!=0)
            return super.getImg_path();
        else return "GHOSTY/GHOSTY" + curNum + ".png";
    }
    public void freeze(){
        this.freeze=10;
    }
}
