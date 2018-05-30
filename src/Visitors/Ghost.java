package Visitors;

import javax.swing.*;

public class Ghost {
   private int x;
   private int y;
   private ImageIcon img;


    public Ghost(int x, int y, String path_img){
       this.img=new ImageIcon(path_img);
       this.x=x;
       this.y=y;
   }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

}
