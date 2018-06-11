package Visitors;

public class SafePacman extends Pacman implements Visited {
    public SafePacman(int x, int y, int lives, int score) {
        super(x, y,"safe_pacman.jpg", lives, score);
    }

    @Override
    public StatusChange accept(Visitor ghost) {
        return ghost.visit(this);
    }
}
