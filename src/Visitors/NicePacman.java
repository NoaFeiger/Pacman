package Visitors;

public class NicePacman extends Pacman implements Visited {
    public NicePacman(int x, int y, int lives, int score) {
        super(x, y, "pacman_open.png", "pacman_close.png", lives, score);
    }

    @Override
    public StatusChange accept(Visitor ghost) {
        return ghost.visit(this);
    }
}
