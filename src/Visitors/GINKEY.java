package Visitors;

public class GINKEY extends Ghost implements Visitor {

    public GINKEY(int x, int y, String path_img) {
        super(x, y, path_img);
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
