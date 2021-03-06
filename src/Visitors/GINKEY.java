package Visitors;

public class GINKEY extends Ghost implements Visitor {

    public GINKEY(int x, int y, int speed) {
        super(x, y, "GINKY.png","GINKY_FROZEN.png", speed, 4);
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //kill pacman
        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //ginkey disappear for 5 sec
        visible=10;
        return new StatusChange(0, 0, 0);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ginkey dies
        this.alive = false;
        return new StatusChange(0, 0, 0);
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
    public void freeze(){
        this.freeze=10;
    }
}
