package Visitors;

import Level.LevelGame;
import Visitors.*;

public class WaterBomb extends Ghost {

    private final directions direction;
    INKY summer;

    public WaterBomb(int x, int y, String normal_path, String frozen_path, int speed, int id, INKY summer) {
        super(x, y, normal_path, frozen_path, speed, id);
        this.summer = summer;
        this.direction = summer.last_direct;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return null;
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        return null;
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) {
        return new StatusChange(0, -1, 0);
    }

    @Override
    public String getPath() {
        return normal_path;
    }

    @Override
    public StatusChange move() {
        directions i = direction;
        switch (i) {
            case RIGHT:
                x_ghost++;
                break;
            case LEFT:
                x_ghost--;
                break;
            case UP:
                y_ghost--;
                break;
            case DOWN:
                y_ghost++;
                break;
        }
        if (x_ghost < 0 || x_ghost > 31 || y_ghost < 0 || y_ghost > 31) {
            LevelGame.ghost_to_remove.add(this);
            summer.setInky_can_shoot(true);
        }
        if (LevelGame.matrix_walls[x_ghost][y_ghost] == 1) {
            LevelGame.ghost_to_remove.add(this);
            summer.setInky_can_shoot(true);
        }
        if (x_ghost == LevelGame.pacManX() && y_ghost == LevelGame.pacManY()) {
            return LevelGame.getPacMan().accept(this);
        }
        return new StatusChange(0, 0, 0);
    }
}