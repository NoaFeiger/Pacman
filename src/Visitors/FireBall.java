package Visitors;

import Level.LevelGame;

public class FireBall extends Ghost implements Visitor {
    private directions direct_ghost;
    private int x_ghost;
    private int y_ghost;
    private String path_img;
    private int speed;
    private int id;
    private BLINKY blinky;

    public FireBall(BLINKY me, int x_ghost, int y_ghost, String path_img, int speed, int id, directions direct_gohst) {
        super(x_ghost, y_ghost, path_img,"", speed, id);
        this.id = id;
        this.blinky = blinky;
        this.direct_ghost = direct_gohst;
        this.path_img = path_img;
        this.speed = speed;
        this.x_ghost = x_ghost;
        this.y_ghost = y_ghost;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return null; // wont happen
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        return null; // wont happen
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // pacman died
        return new StatusChange(0, -1, 0);
    }

    @Override
    public StatusChange move() {
        StatusChange statusChange = null;
        // create a list of all moves possibilities
        int tmp_x = this.x_ghost;
        int tmp_y = this.y_ghost;
        switch (direct_ghost) {
            case UP: {
                this.y_ghost--;
                break;
            }
            case DOWN: {
                this.y_ghost++;
                break;
            }
            case LEFT: {
                this.x_ghost--;
                break;
            }
            case RIGHT: {
                this.x_ghost++;
                break;
            }
        }
        if ((this.x_ghost <=1 || this.y_ghost >= 30 || this.x_ghost >= 30 || this.y_ghost <= 1)) {//alive
            LevelGame.ghost_to_remove.add(this);
            blinky.setBlinky_can_shoot(true);
        }
        else { // alive
          x_ghost=tmp_x;
          y_ghost=tmp_y;
        }
        if(LevelGame.matrix[this.x_ghost][this.y_ghost]==2){
            System.out.println("HIT");
            statusChange = LevelGame.getPacMan().accept(this);
        }
        return statusChange;
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}
