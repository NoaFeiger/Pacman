package Visitors;

import Level.LevelGame;

public class WaterBomb extends Ghost {

    private final directions direction;
    INKY ghost_creator;

    public WaterBomb(int x, int y, String normal_path, String frozen_path, int speed, int id, INKY summer) {
        super(x, y, normal_path, frozen_path, speed, id);
        this.ghost_creator = summer;
        this.direction = summer.last_direct;
    }
    @Override
    public StatusChange visit(NicePacman nice_p) {
        return null; // wont happen
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        //freeze- 3 sec lose 10 points
        return new StatusChange(-10, 0, 30);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // ghost Freeze for 5 sec
        ghost_creator.freeze(50);
        return null; //TODO check
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
            ghost_creator.setInky_can_shoot(true);
        }
        if (LevelGame.matrix_walls[x_ghost][y_ghost] == 1) {
            LevelGame.ghost_to_remove.add(this);
            ghost_creator.setInky_can_shoot(true);
        }
        if (x_ghost == LevelGame.pacManX() && y_ghost == LevelGame.pacManY()) {
            return LevelGame.getPacMan().accept(this);
        }
        return new StatusChange(0, 0, 0);
    }
}