package Visitors;

public class INKY extends Ghost implements Visitor {

    public INKY(int x, int y) {
        super(x, y, "INKY.jpg");
    }

    @Override
    public void visit(NicePacman nice_p) {
    }

    @Override
    public void visit(SafePacman safe_p) {
        safe_p.setScore(safe_p.getScore()-10);
        // freeze to 3 seconds

    }

    @Override
    public void visit(AngryPacman nice_p) {
        //freeze to 5 seconds
    }
}
