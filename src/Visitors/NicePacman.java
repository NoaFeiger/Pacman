package Visitors;

public class NicePacman extends Pacman implements Visited {
    public NicePacman(int x, int y, int lives, int score) {
        super(x, y, "nice_pacman.png", lives, score);
    }

    @Override
    public void accept(Visitor ghost) {
        ghost.visit(this);
    }
}
