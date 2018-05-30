package Main;

import Level1.Level1Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class Main extends JFrame {
    public static void main(String[] args) {
        Main main=new Main();
    }

    public Main() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Pac-man");
        setVisible(true);

        JPanelB main_panel = new JPanelB();
        main_panel.setAlignmentX(1);
        JLabel title=new JLabel("Pac-man IL");
        title.setSize(80,100);
        title.setHorizontalAlignment(0);
        title.setFont(new Font("Playbill", Font.BOLD, 60));
        title.setForeground(Color.white);

        JButton button_start = new JButton(new ImageIcon("button_start.png"));
        button_start.setOpaque(false); // remove the background of jbutton-random
        button_start.setContentAreaFilled(false);
        button_start.addActionListener(e -> {   //moves to the option window panel
            remove(main_panel);
            Level1Game a = new Level1Game(this,main_panel);
          //  this.addKeyListener(a);
            add(a);
            repaint();
            revalidate();});

        JButton button_exit = new JButton(new ImageIcon("button_exit.png"));
        button_exit.setOpaque(false); // remove the background of the button
        button_exit.setContentAreaFilled(false);
        button_exit.addActionListener(e ->
            System.exit(42));
        main_panel.setLayout(new GridLayout(5,2));
        main_panel.add(title);
        main_panel.add(new JLabel(""));
        main_panel.add(button_start);
        main_panel.add(button_exit);
        this.add(main_panel);
        pack();
        this.setSize(800, 800);

    }

}