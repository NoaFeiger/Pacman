package Visitors;

public class GINKEY extends Ghost implements Visitor {

    private int visible;

    public GINKEY(int x, int y,int speed) {
        super(x, y,"GINKY.jpg",speed);
        this.visible = 0;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) { //kill pacman
        return new StatusChange(0,-1,0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { //ginkey disappear for 5 sec
        this.visible = 5;
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {//ginkey dies for 5 sec
        this.alive = false;
        return new StatusChange(0,0,0);
    }
}
