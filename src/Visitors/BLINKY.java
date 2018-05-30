package Visitors;

public class BLINKY extends Ghost implements Visitor {

    public BLINKY(int x, int y) {
        super(x, y, "BLINKY.jpg");
    }

    @Override
    public void visit(NicePacman nice_p) {
        System.out.println("blinky got nice");
    }

    @Override
    public void visit(SafePacman nice_p) {
        System.out.println("blinky got safe");


    }

    @Override
    public void visit(AngryPacman nice_p) {
        System.out.println("blinky got angry");

    }
}
