package Visitors;

public class RANDOMY extends Ghost implements Visitor {
    private int curNum;
    public RANDOMY(int x, int y, int speed) {
        super(x, y, "SNAP/SNAP0.png","GINKY_FROZEN.png", speed, 4);
        curNum = 0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //kill pacman or not
        boolean toKill = Math.random()<0.5;
        int lifes = 0;
        if(toKill) lifes = -1;
        return new StatusChange(-40, lifes, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //Random points reduced, and random freezing time.
        int toFreeze = (int)(Math.random()*10)+5;
        return new StatusChange(-(int)(Math.random()*100), 0, toFreeze);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ghost dies
        this.alive = false;
        return new StatusChange(0, 0, 5);
    }
    @Override
    public String getPath() {
        curNum = (curNum+1)%10;
        //Random speed
        this.setSpeed(Math.min((int)(Math.random()*6),3));
        if(this.freeze!=0)
            return super.getImg_path();
        else return "SNAP/SNAP" + curNum + ".png";

    }
    public void freeze(){
        this.freeze=10;
    }
}
