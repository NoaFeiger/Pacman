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
    public static boolean can_shoot;
    private boolean dead;

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
        if(dead){
            LevelGame.matrix[x][y]=0;
            LevelGame.Vmatrix=null;
            return null;
        }
        System.out.println(x+" "+y);
        StatusChange statusChange = null;
        int tmp_x = x;
        int tmp_y = y;

        switch (direct_ghost) {
            case UP: {
                y--;
                break;
            }
            case DOWN: {
                y++;
                break;
            }
            case LEFT: {
                x--;
                break;
            }
            case RIGHT: {
                x++;
                break;
            }
        }
        if(LevelGame.matrix[x][y]!='c'-'0') {
            if (!(x < 0 || y < 0 || x > 31 || y > 31)) {
                if (LevelGame.matrix[x][y] == 2) {
                    System.out.println("HIT");
                    statusChange = LevelGame.getPacMan().accept(this);
                    return statusChange;
                }
                if (!(LevelGame.matrix[x][y] == 1))
                {
                    if (!(LevelGame.matrix[x][y] == 4 || LevelGame.matrix[x][y] == 8)) { //if encountered a monster - dont save it
                        LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
                        LevelGame.matrix[tmp_x][tmp_y] = tempnum;
                    }
                    temp = LevelGame.Vmatrix[x][y];
                    tempnum = LevelGame.matrix[x][y];
                    LevelGame.matrix[x][y] = id; // new place of GINKEY
                    LevelGame.Vmatrix[x][y] = this;
                    return statusChange;

                }
                else {
                    LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
                    LevelGame.matrix[tmp_x][tmp_y] = tempnum;
                    x = tmp_x;
                    y = tmp_y;
                    dead = true;
                    this.blinky.setCan_shoot_fire(true);
                    LevelGame.ghost_to_remove.add(this);
                    return statusChange;
                }
            }
        }
        dead=true;
        this.blinky.setCan_shoot_fire(true);
        return null;
    }
    /*
    @Override
    public StatusChange move() {
        StatusChange statusChange = null;
        // create a list of all moves possibilities
        int tmp_x = x;
        int tmp_y = y;
        switch (direct_ghost) {
            case UP: {
                y--;
                break;
            }
            case DOWN: {
                y++;
                break;
            }
            case LEFT: {
                x--;
                break;
            }
            case RIGHT: {
                x++;
                break;
            }
        }
        if (!(x < 0 || y < 0 || x > 31 || y > 31)) {
            LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
            LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            temp = LevelGame.Vmatrix[x][y];
            tempnum = LevelGame.matrix[x][y];
            LevelGame.matrix[x][y] = id; // new place of GINKEY
            LevelGame.Vmatrix[x][y] = LevelGame.Vmatrix[tmp_x][tmp_y];

        } else { // corner
            LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
            LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            LevelGame.ghost_to_remove.add(this);
            blinky.setCan_shoot_fire(true);

        }
        if(LevelGame.matrix[x][y]==2){
            System.out.println("HIT");
            statusChange = LevelGame.getPacMan().accept(this);
        }
        return statusChange;
    }
    */
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}
