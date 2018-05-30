package Visitors;

public interface Visitor { //Ghost
    void visit(NicePacman nice_p);
    void visit(SafePacman nice_p);
    void visit(AngryPacman nice_p);

}
