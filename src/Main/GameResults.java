package Main;

import javax.swing.*;
import java.awt.*;

public class GameResults extends JPanelB{
    private JFrame frame;
    private JPanel main_panel;
    public GameResults(JFrame frame,JPanel main_panel){
    setVisible(true);
    this.frame=frame;
    this.main_panel=main_panel;
    JPanel panel = new JPanel(new GridLayout(4, 2));
    JLabel label=new JLabel("Insert your Name:");
    label.setFont(new Font("Playbill", Font.BOLD, 60));
    JTextField text=new JTextField();
    JButton button_approve=new JButton("Agree");
        button_approve.addActionListener(e -> { frame.remove(this);
            frame.add(main_panel);frame.repaint();frame.revalidate();});
    panel.add(label);
    panel.add(text);
    panel.add(button_approve);
    this.add(panel);
    }
}
