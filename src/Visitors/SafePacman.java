package Visitors;

public class SafePacman extends Pacman implements Visited {
    public SafePacman(int x, int y, int lives, int score) {
        super(x, y,"safe_pacman.png", lives, score);
    }

    @Override
    public void accept(Visitor ghost) {
        ghost.visit(this);
    }
}
