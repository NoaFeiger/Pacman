package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JPanelB extends JPanel {

    protected Image backgroundImage;

    // initialize the background image.
    public JPanelB()  {
        try {
            backgroundImage = ImageIO.read(new File("pacman_background.jpg"));
          //  backgroundImage = ImageIO.read(new File("orig.gif"));
            backgroundImage= backgroundImage.getScaledInstance(800,600,Image.SCALE_DEFAULT);
        }
        catch (Exception e)
        {
        }
    }

    public JPanelB(FlowLayout flowLayout) {
        super(flowLayout);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
