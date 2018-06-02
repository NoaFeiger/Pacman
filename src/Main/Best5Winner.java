package Main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Best5Winner extends JPanelB {
    private JFrame frame;
    private JPanel main_panel;
    private ArrayList<String> array_scores;
    private String[]best5;
    public Best5Winner(JFrame frame,JPanel main_panel){
        super();
        this.frame=frame;
        this.array_scores =new ArrayList<>();
        this.main_panel=main_panel;
        setVisible(true);
        JPanel panel_best5=new JPanel(new GridLayout(5,2));

        panel_best5.setOpaque(false);
        buildArray();
        JLabel title=new JLabel("Top 5 Players");
        title.setFont(new Font("Playbill", Font.BOLD, 60));
        title.setForeground(Color.WHITE);
        panel_best5.add(title);
        Collections.sort(array_scores, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (splitScore(o1)-splitScore(o2));
            }
        });
        this.best5=new String[]{"","","","",""};
        int size=array_scores.size();
        size--;
        int counter=0;
        while (size>=0&&counter<5){
            best5[counter]=array_scores.get(size);
            size--;
        }
        JLabel label = new JLabel("<html>"+best5[0]+"<br>"+best5[1]+"<br>"+best5[2]+"<br>"+best5[3]+"<br>"+best5[4]+"</html>");
        label.setFont(new Font("Playbill", Font.BOLD, 40));
        label.setForeground(Color.white);
        label.setOpaque(false);
        panel_best5.add(label);

        JButton button_back=new JButton(new ImageIcon("button_back.png"));
        button_back.setOpaque(false); // remove the background of jbutton-random
        button_back.setContentAreaFilled(false);
        panel_best5.add(button_back);

        button_back.addActionListener(e -> {   //moves to the option window panel
            frame.remove(this);
                frame.add(main_panel);frame.repaint();frame.revalidate();});
        this.add(panel_best5);
    }

    private void buildArray() {
        BufferedReader reader = null;
        try {
            File file = new File("results.txt");
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                array_scores.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int splitScore(String line) {
        String[] parts = line.split(",");
        String score = parts[1];
        return Integer.parseInt(score);

    }

}

