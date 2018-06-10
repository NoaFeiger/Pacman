package Visitors;

import Level.LevelGame;

import java.util.ArrayList;
import java.util.HashSet;

public class WaterBomb  extends Ghost implements Visitor {

    private int x_ghost;
    private int y_ghost;
    private String path_img;
    private int speed;
    private int id;
    private directions direct_ghost;
    private INKY ghost;
    public WaterBomb(INKY me,int x_ghost,int y_ghost,String path_img,int speed,int id, directions direct_ghost){
        super(x_ghost, y_ghost,path_img,path_img,speed,id);
        this.id=id;
        this.direct_ghost=direct_ghost;
        this.ghost=me;
        this.path_img=path_img;
        this.speed=speed;
        this.x_ghost=x_ghost;
        this.y_ghost=y_ghost;
        temp=LevelGame.Vmatrix[x_ghost][y_ghost];
        tempnum=LevelGame.matrix[x_ghost][y_ghost];
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        return null; // wont happen
    }

    @Override
    public StatusChange visit(SafePacman safe_p) {
        //freeze- 3 sec lose 10 points
        return new StatusChange(-10,0,30);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // ghost Freeze for 5 sec
        ghost.freeze(50);
        return  null; //TODO check
    }
    @Override
    public StatusChange move() {
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
        if (!(x < 0 || y < 0 || x > 31 || y > 31)) {
            if(LevelGame.matrix[x][y]==2){
                System.out.println("HIT");
                statusChange =  LevelGame.getPacMan().accept(this);
            }
            if (!(LevelGame.matrix[x][y] == 1)) {
                if(!(LevelGame.matrix[x][y]==4||LevelGame.matrix[x][y]==8)) { //if encountered a monster - dont save it
                    LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
                    LevelGame.matrix[tmp_x][tmp_y] = tempnum;
                }
                temp = LevelGame.Vmatrix[x][y];
                tempnum = LevelGame.matrix[x][y];
                LevelGame.matrix[x][y] = id; // new place of GINKEY
                LevelGame.Vmatrix[x][y] = this;
            } else {
                LevelGame.Vmatrix[tmp_x][tmp_y] = temp;
                LevelGame.matrix[tmp_x][tmp_y] = tempnum;
                LevelGame.Vmatrix[x][y] = null;
                LevelGame.matrix[x][y] = 1;
                LevelGame.ghost_to_remove.add(this);
            }
        }
        return statusChange;
    }
    @Override
    public String getPath() {
        return super.getImg_path();
    }
}


