package Level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelGame extends JPanel implements ActionListener {
    private JFrame frame;
    private JPanel main_panel;
    private int[][] matrix;
    private Timer timer;
    private int x; // pacman place
    private int y;
    protected int vx;
    protected int vy;
    private final int delay = 200;

    public LevelGame(JFrame frame, JPanel main_panel,int level,String path_board) {
        super();
        this.frame = frame;
        this.main_panel = main_panel;
        this.matrix = new int[32][32];
        vx = 0; vy = 0;
        buildMatrix(path_board);
        timer = new Timer(delay, this);
        timer.start();

        addKeyListener(KeyEvent.VK_UP,0,-1);
        addKeyListener(KeyEvent.VK_DOWN,0,1);
        addKeyListener(KeyEvent.VK_LEFT,-1,0);
        addKeyListener(KeyEvent.VK_RIGHT,1,0);
    }

    private void buildMatrix(String path_board) {
        BufferedReader reader = null;
        try {
            File file = new File(path_board);
            reader = new BufferedReader(new FileReader(file));
            String line;
            int j=0;
            while ((line = reader.readLine()) != null) {
                buildLine(line,j);
                j++;
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

    private void buildLine(String line, int j) {
        for(int i=0;i<line.length();i++){
            matrix[i][j]=line.charAt(i)-'0';
        }
    }

    private void addKeyListener(int keyEvent,int mvx,int mvy) {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyEvent, 0), "forward"+keyEvent);
        this.getActionMap().put("forward"+keyEvent, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vy=mvy;
                vx=mvx;
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        // ImageIcon img=new ImageIcon("pacman.jpg");
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * 20, j * 20, 20, 20);
                } else if (matrix[i][j] == 2) {
                    x=i;y=j;
                    g.setColor(Color.YELLOW);
                    g.fillRect(i * 20, j * 20, 20, 20);
                } else if (matrix[i][j] == 3) {
                    g.setColor(Color.PINK);
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
                else if (matrix[i][j] == 4) {
                    g.setColor(Color.RED);
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int tmp_x;
        int tmp_y;
        if (timer == e.getSource()) {
            tmp_x = (x + vx) % 32;
            tmp_y = (y + vy) % 32;
            if(matrix[tmp_x][tmp_y]!=1){//not a block
                matrix[x][y] = 0;
                x = tmp_x;
                y = tmp_y;
            }
            if(x<=-1)
                x=31;
            if(y<=-1)
                y=31;

            matrix[x][y] = 2;
            repaint();
        }
    }
}


