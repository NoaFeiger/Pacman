package Visitors;

public class INKY extends Ghost implements Visitor {
    private int freeze;
    public INKY(int x, int y,int speed) {
        super(x, y, "INKY.jpg",speed);
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        return new StatusChange(-10,0,3);
    }

    @Override
    public StatusChange visit(AngryPacman nice_p) {
        this.freeze = 5;
        return new StatusChange(0,0,0);
    }
}
