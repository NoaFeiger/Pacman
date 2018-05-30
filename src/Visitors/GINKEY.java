package Visitors;

public class GINKEY extends Ghost implements Visitor {

    public GINKEY(int x, int y) {
        super(x, y,"GINKY.jpg");
    }

    @Override
    public void visit(NicePacman nice_p) { //keel pacman
        nice_p.setLives(nice_p.getLives()-1);
    }

    @Override
    public void visit(SafePacman safe_p) { //ginkey disappear for 5 sec

    }

    @Override
    public void visit(AngryPacman angry_p) {//ginkey dies for 5 sec

    }
}
