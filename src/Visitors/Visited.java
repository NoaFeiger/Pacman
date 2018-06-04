package Visitors;

public interface Visited { // PacMan
    StatusChange accept(Visitor ghost);
}