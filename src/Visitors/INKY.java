package Visitors;

import Level.LevelGame;


public class INKY extends Ghost implements Visitor {
    private int counter;

    public boolean isInky_can_shoot() {
        return inky_can_shoot;
    }

    public void setInky_can_shoot(boolean inky_can_shoot) {
        this.inky_can_shoot = inky_can_shoot;
    }

    private boolean inky_can_shoot;

    public INKY(int x, int y, int speed) {
        super(x, y, "INKY.png", "INKY_FROZEN.png", speed, 8);
        counter = 0;
        this.inky_can_shoot = true;
    }

    @Override
    public StatusChange visit(NicePacman nice_p) {
        this.setBeenCorner(false);
        return new StatusChange(0, 0, 0);
    }

    @Override
    public StatusChange visit(SafePacman safe_p) { // pacman lose 10 points and freeze for 3 sec
        this.setBeenCorner(false);
        return new StatusChange(-10, 0, 10);
    }

    @Override
    public StatusChange visit(AngryPacman angry_p) { // inky freeze for 5 sec
        this.setBeenCorner(false);
        this.freeze = 10;
        return new StatusChange(0, 0, 0);
    }

    @Override
    public String getPath() {
        return super.getImg_path();
    }

    public void freeze(int sec) {
        this.freeze = sec;
    }

    @Override
    public StatusChange move() {
        StatusChange statusChange = super.move();

        if (inky_can_shoot) {
            WaterBomb water_bomb;
            water_bomb = new WaterBomb(x_ghost,y_ghost,"water_bomb.png","Snowflake.png",1,9,this);//TODO check if it goes on the right direction
            LevelGame.tmp_array.add(water_bomb);
            this.inky_can_shoot=false;
            /*
            switch (cur) {
                case UP: {
                    if (LevelGame.matrix_walls[x_ghost][y_ghost - 1] != 1) {
                        water_bomb = new WaterBomb(this, x_ghost, y_ghost - 1, "water_bomb.png", 2, 9, cur);//TODO check if it goes on the right direction
                        LevelGame.tmp_array.add(water_bomb);
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        this.inky_can_shoot = false;
                    }
                    break;
                }
                case DOWN: {
                    if (LevelGame.matrix_walls[x_ghost][y_ghost + 1] != 1) {
                        water_bomb = new WaterBomb(this, x_ghost, y_ghost + 1, "water_bomb.png", 2, 9, cur);//TODO check if it goes on the right direction
                        LevelGame.tmp_array.add(water_bomb);
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        this.inky_can_shoot = false;
                    }
                    break;
                }
                case LEFT: {
                    if (LevelGame.matrix_walls[x_ghost - 1][y_ghost] != 1) {
                        water_bomb = new WaterBomb(this, x_ghost - 1, y_ghost, "water_bomb.png", 2, 9, cur);//TODO check if it goes on the right direction
                        LevelGame.tmp_array.add(water_bomb);
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        this.inky_can_shoot = false;

                    }
                    break;
                }
                default: { //RIGHT
                    if (LevelGame.matrix_walls[x_ghost + 1][y_ghost] != 1) {
                        water_bomb = new WaterBomb(this, x_ghost + 1, y_ghost, "water_bomb.png", 2, 9, cur);//TODO check if it goes on the right direction
                        LevelGame.tmp_array.add(water_bomb);
                        if (statusChange == null)
                            statusChange = water_bomb.move();
                        this.inky_can_shoot = false;
                    }
                    break;
                }
            }*/
        }
        return statusChange;
    }


}