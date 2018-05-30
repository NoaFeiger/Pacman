package Visitors;

public class AngryPacman extends Pacman implements Visited {
    public AngryPacman(int x, int y, int lives, int score) {
        super(x, y, "angry_pacman.png", lives, score);
    }

    @Override
    public void accept(Visitor ghost) {
        ghost.visit(this);
    }
}
