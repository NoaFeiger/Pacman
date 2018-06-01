package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class MyTimer implements ActionListener {
    private static MyTimer instance;
    private Timer timer;
    List<TimerListener> listeners;

    public static MyTimer getInstance(){
        if(instance==null)
            instance = new MyTimer();
        return instance;
    }

    private MyTimer(){
        timer = new Timer(100, this);

        listeners = new ArrayList<>();
        timer.start();
    }
    public void addListener(TimerListener listener){
        listeners.add(listener);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(TimerListener l : listeners)
            l.action();
    }

}

