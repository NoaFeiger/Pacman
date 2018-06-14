package Visitors;

public class AngryPacman extends Pacman implements Visited {
    public AngryPacman(int x, int y, int lives, int score) {
        super(x, y, "angry_pacman_open.png", "angry_pacman_close.png", lives, score);
    }
    @Override
    public StatusChange accept(Visitor ghost) {
       return ghost.visit(this);
    }
}
