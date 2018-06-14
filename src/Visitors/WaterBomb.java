package Visitors;

import Level.LevelGame;


public class WaterBomb  extends Ghost implements Visitor {

    private int x_ghost;
    private int y_ghost;
    private String path_img;
    private int speed;
    private int id;
    private directions direct_ghost;
    private INKY ghost;
    private boolean dead;

    public WaterBomb(INKY me, int x_ghost, int y_ghost, String path_img, int speed, int id, directions direct_ghost) {
        super(x_ghost, y_ghost, path_img, path_img, speed, id);
        this.id = id;
        this.direct_ghost = direct_ghost;
        this.ghost = me;
        this.path_img = path_img;
        this.speed = speed;
        this.x_ghost = x_ghost;
        this.y_ghost = y_ghost;
      //  temp = LevelGame.array_ghost[x_ghost][y_ghost];
       // tempnum = LevelGame.matrix[x_ghost][y_ghost];
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return null; // wont happen
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        //freeze- 3 sec lose 10 points
        if (dead) return null;
        return new StatusChange(-10, 0, 30);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // ghost Freeze for 5 sec
        if (dead) return null;
        ghost.freeze(50);
        return null; //TODO check
    }

    @Override
    public StatusChange move() {
        if (dead) {
          //  LevelGame.matrix[this.x_ghost][this.y_ghost] = 0;
           // LevelGame.array_ghost = null;
           LevelGame.ghost_to_remove.add(this);
            return null;
        }
        System.out.println(this.x_ghost + " " + this.y_ghost);
        StatusChange statusChange = null;
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
       /* if (!(this.x_ghost < 0 || this.y_ghost < 0 || this.x_ghost > 31 || this.y_ghost > 31)) {
            if(LevelGame.matrix[this.x_ghost][this.y_ghost]==2){
                System.out.println("HIT");
                statusChange =  LevelGame.getPacMan().accept(this);
                return statusChange;
            }
            */
        if (!(LevelGame.matrix_walls[this.x_ghost][this.y_ghost] == 1)) {
            //     if(!(LevelGame.matrix[this.x_ghost][this.y_ghost]==4||LevelGame.matrix[this.x_ghost][this.y_ghost]==8)) { //if encountered a monster - dont save it
            //       LevelGame.array_ghost[tmp_x][tmp_y] = temp;
            //     LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            //}
            //   temp = LevelGame.array_ghost[this.x_ghost][this.y_ghost];
            // tempnum = LevelGame.matrix[this.x_ghost][this.y_ghost];
            // LevelGame.matrix[this.x_ghost][this.y_ghost] = id; // new place of GINKEY
            // LevelGame.array_ghost[this.x_ghost][this.y_ghost] = this;
            // return statusChange;
            x_ghost = tmp_x;
            y_ghost = tmp_y;
        }
        else { //died
            // LevelGame.array_ghost[tmp_x][tmp_y] = temp;
            // LevelGame.matrix[tmp_x][tmp_y] = tempnum;
            //LevelGame.array_ghost[x_ghost][y_ghost] = null;
            //LevelGame.matrix[x_ghost][y_ghost] = 1;
            dead = true;
            LevelGame.ghost_to_remove.remove(this);
            //  LevelGame.ghost_to_remove.add(this);
             return statusChange;
        }
    //  dead=true;
        return null;
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}


