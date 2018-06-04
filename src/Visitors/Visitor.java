package Visitors;

public interface Visitor { //Ghost
    StatusChange visit(NicePacman nice_p);
    StatusChange visit(SafePacman safe_p);
    StatusChange visit(AngryPacman angry_p);
    String getPath();
}
