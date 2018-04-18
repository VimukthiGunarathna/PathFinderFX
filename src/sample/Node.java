package sample;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Node {

    static GridPane gridPane;
    static int aX, aY, bX, bY;
    //static Node[][] grid = Node.getGrid();
    static ArrayList<Integer[]> path = new ArrayList<Integer[]>();// contains cells that were travelled from
    static PriorityQueue<Node> open;// stores visitable (unvisited) cells
    static boolean closed[][];// 'visited' status of cells

}
