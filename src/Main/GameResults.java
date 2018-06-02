package Main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class GameResults extends JPanelB{
    private JFrame frame;
    private JPanel main_panel;
    private int points;
    public GameResults(JFrame frame,JPanel main_panel,int points){
    setVisible(true);
    this.frame=frame;
    this.main_panel=main_panel;
    this.points=points;
    JPanel panel = new JPanel(new GridLayout(4, 2));
    JLabel label=new JLabel("Insert your Name:");
    label.setFont(new Font("Playbill", Font.BOLD, 60));
    JTextField text=new JTextField();
    JButton button_approve = new JButton(new ImageIcon("button_agree.png"));
    button_approve.setOpaque(false); // remove the background of jbutton-random
    button_approve.setContentAreaFilled(false);
        button_approve.addActionListener(e -> {
            SaveDetail(text);
            frame.remove(this);
            frame.add(main_panel);frame.repaint();frame.revalidate();});
    panel.add(label);
    panel.add(text);
    panel.add(button_approve);
    this.add(panel);
    }

    private void SaveDetail(JTextField text) {
        String name= text.getText();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("results.txt"));
            writer.write(name+","+this.points);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }
    }




