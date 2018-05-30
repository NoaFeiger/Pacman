package Visitors;

import javax.swing.*;

public class Ghost {
   private int x;
   private int y;
   private ImageIcon img;

   public Ghost(int x,int y, String path_img){
       this.img=new ImageIcon(path_img);
       this.x=x;
       this.y=y;
   }
}
