package Visitors;

public class GINKEY extends Ghost implements Visitor {

    public GINKEY(int x, int y) {
        super(x, y,"GINKY.jpg");
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
