package Visitors;

public class demo {
    public static void main(String[] args)
    {
        Visitor l1 = new BLINKY();
        Visited a1 = new NicePacman();
        Visited a2 = new SafePacman();
        Visited a3 = new AngryPacman();
        a1.accept(l1);
        a2.accept(l1);
        a3.accept(l1);
    }
}
