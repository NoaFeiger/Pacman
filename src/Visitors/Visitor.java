package Visitors;

public interface Visitor { //Ghost
    StatusChange visit(NicePacman nice_p);
    StatusChange visit(SafePacman nice_p);
    StatusChange visit(AngryPacman nice_p);
}
