package Visitors;

public class RANDOMY extends Ghost implements Visitor {
    private int curNum;
    public RANDOMY(int x, int y, int speed) {
        super(x, y, "GINKY.png","GHOSTY1.png", 1, 4);
        curNum = 0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //kill pacman
        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //ginkey disappear for 5 sec
        return new StatusChange(-(int)(Math.random()*50), (int)(Math.random()), 0);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ginkey dies
        this.alive = false;
        return new StatusChange(0, 0, 0);
    }
    @Override
    public String getPath() {
        curNum = (curNum%5)+1;
        this.setSpeed(Math.min((int)(Math.random()*10),5));
        if(this.freeze!=0)
            return super.getImg_path();
        else return "GHOSTY" + curNum + ".png";
    }
    public void freeze(){
        this.freeze=10;
    }
}
