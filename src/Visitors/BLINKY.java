package Visitors;

public class BLINKY extends Ghost implements Visitor {

    public BLINKY(int x, int y, String path_img) {
        super(x, y, path_img);
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
