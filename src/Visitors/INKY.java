package Visitors;

public class INKY extends Ghost implements Visitor {

    public INKY(int x, int y) {
        super(x, y, "INKY.jpg");
    }

    @Override
    public void visit(NicePacman nice_p) {

    }

    @Override
    public void visit(SafePacman nice_p) {

    }

    @Override
    public void visit(AngryPacman nice_p) {

    }
}
