package Main;

import javax.swing.*;
import java.awt.*;

public class LastPage extends JPanelB {

   // public static void main(String[]args){
     //   LastPage last=new LastPage();
    //}
   private JFrame frame;
   private JPanel main_panel;
   private int points;
    public LastPage(JFrame frame,JPanel main_page, int points) {
        super();
        this.frame=frame;
        this.main_panel=main_page;
        setVisible(true);
        JPanelB panel = new JPanelB();
        panel.setAlignmentX(1);
        JLabel title = new JLabel("GameOver");
        title.setSize(50, 50);
        title.setHorizontalAlignment(0);
        title.setFont(new Font("Playbill", Font.BOLD, 60));
        title.setForeground(Color.white);
        this.points=points;
        JButton button_save_result = new JButton(new ImageIcon("button_save-result.png"));
        button_save_result.setOpaque(false); // remove the background of jbutton-random
        button_save_result.setContentAreaFilled(false);
        button_save_result.addActionListener(e -> {   //moves to the option window panel
            remove(panel);
            GameResults game_results = new GameResults(this.frame,this.main_panel);
            frame.remove(this); // move to the game page
            frame.add(game_results);
            frame.repaint();
            frame.revalidate();

        });

        JButton button_exit = new JButton(new ImageIcon("button_exit2.png"));
        button_exit.setOpaque(false); // remove the background of the button
        button_exit.setContentAreaFilled(false);
        button_exit.addActionListener(e ->
                System.exit(42));
        JButton button_main_menu = new JButton(new ImageIcon("button_main-menu.png"));
        button_main_menu.setOpaque(false); // remove the background of jbutton-random
        button_main_menu.setContentAreaFilled(false);
        button_main_menu.addActionListener(e -> { frame.remove(this);
            frame.add(main_panel);frame.repaint();frame.revalidate();});



        panel.setLayout(new GridLayout(5, 2));
        panel.add(title);
        panel.add(new JLabel(""));
        panel.add(button_save_result);
        panel.add(button_main_menu);
        panel.add(button_exit);
        this.add(panel);

    }
}

