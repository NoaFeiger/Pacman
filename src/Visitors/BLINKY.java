package Visitors;

public class BLINKY extends Ghost implements Visitor {

    public BLINKY(int x, int y,int speed) {
        super(x, y, "BLINKY.jpg",speed);
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(SafePacman nice_p) {
        return new StatusChange(0,0,0);
    }

    @Override
    public StatusChange visit(AngryPacman angryPacman_p) { //kills pacman
        System.out.println("PacMan dies");
        angryPacman_p.setLives(angryPacman_p.getLives()-1);

        return new StatusChange(0,-1,0);
    }
}
