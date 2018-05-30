package Visitors;

public class BLINKY extends Ghost implements Visitor {

    public BLINKY(int x, int y) {
        super(x, y, "BLINKY.jpg");
    }

    @Override
    public void visit(NicePacman nice_p) {
    }

    @Override
    public void visit(SafePacman nice_p) {
    }

    @Override
    public void visit(AngryPacman angryPacman_p) { //kills pacman
        System.out.println("pacman kills");
        angryPacman_p.setLives(angryPacman_p.getLives()-1);



    }
}
