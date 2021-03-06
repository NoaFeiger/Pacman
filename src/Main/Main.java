package Main;
import Level.LevelGame;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class Main extends JFrame {
    private JComboBox<String> cb = null;

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
            GameControl gameControl = new GameControl(this,main_panel,cb.getSelectedIndex()+1 );
            gameControl.startGame();});

        JButton button_best5 = new JButton(new ImageIcon("button_top-players.png"));
        button_best5.setOpaque(false); // remove the background of jbutton-random
        button_best5.setContentAreaFilled(false);
        button_best5.addActionListener(e -> {   //moves to the option window panel
            remove(main_panel);
            add(new Best5Winner(this,main_panel));
            repaint();
           revalidate();

           });

        JButton button_exit = new JButton(new ImageIcon("button_exit.png"));
        button_exit.setOpaque(false); // remove the background of the button
        button_exit.setContentAreaFilled(false);
        button_exit.addActionListener(e ->
            System.exit(42));
        main_panel.setLayout(new GridLayout(6,2));
        main_panel.add(title);
     //   main_panel.add(new JLabel(""));
        JPanel l1 = new JPanel(new GridLayout(2,1));

        JPanel l2 = new JPanel();
        l2.setOpaque(false);
        l1.setOpaque(false);
        String[] choices = { "Level 1","Level 2", "Level 3"};
        cb = new JComboBox<>(choices);
        JLabel choose = new JLabel("   Choose the Level and then press start");
        choose.setFont(new Font("Playbill", Font.BOLD, 30));

        choose.setForeground(Color.white);
        l1.add(choose);
        l2.add(cb);
        l1.add(l2);
        main_panel.add(l1);
        main_panel.add(button_start);
        main_panel.add(button_best5);
        main_panel.add(button_exit);
        this.add(main_panel);
        pack();
        this.setSize(800, 800);

    }

}