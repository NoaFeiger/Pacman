package Visitors;

public class SafePacman extends Pacman implements Visited {
    public SafePacman(int x, int y, String path, int lives, int score) {
        super(x, y, path, lives, score);
    }

    @Override
    public void accept(Visitor ghost) {
        ghost.visit(this);
    }
}
