package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFinder {

    private static final int RADIUS = 7;
    private static double dCost;          //Diagnol cost
    private static double ver_hori_cost; //Vertical and horizontal cost
    private static String metrics;
    static Grid[][] grid = Grid.getGrid();

    // stores visitable (unvisited) cells
    private static PriorityQueue<Grid> open;

    // 'visited' status of cells
    private static boolean closed[][];


    /**
     * This method sets the metrics and sets the
     * G value to each selected metrics
     *
     * @param m : user selected metrics
     */
    public static void setMetrics(String m) {
        switch (m) {
            case "Manhattan":
                // manhattan
                dCost = 2; //Diagonal cost
                ver_hori_cost = 1; //Vertical/Horizontal cost
                break;

            case "Euclidean":
                // euclidean
                dCost = Math.sqrt(2.0); //Diagonal cost
                ver_hori_cost = 1; //Vertical/Horizontal cost
                break;
            case "Chebyshev":
                // chebyshev
                dCost = 1; //Diagonal cost
                ver_hori_cost = 1; //Vertical/Horizontal cost
                break;
        }
        //Setting the metrics
        metrics = m;
        System.out.println("Metrics: " + m);
    }


    /**
     * This method draws circles which shows the
     * shortest path.
     *
     * @param x1   : user entered starting point 'x' co-ordinate
     * @param y1   : user entered starting point 'y' co-ordinate
     * @param x2   : user entered ending point 'x' co-ordinate
     * @param y2   : user entered ending point 'y' co-ordinate
     * @param path : Integer arraylist that contains the shortest path's
     *             'x','y' co-ordinates.
     */
    public static void drawPath(int x1, int y1, int x2, int y2, ArrayList<Integer[]> path) {

        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {

                Circle path_circle = new Circle(RADIUS, Color.BLUE);


                /**
                 * This loop checks whether a cell is in the arraylist which
                 *  contains the shortest path.
                 */
                boolean cont = false;
                for (int m = 0; m < path.size(); m++) {
                    Integer[] temp = path.get(m);
                    if (temp[0] == i && temp[1] == j) {
                        cont = true;
                        if (i == x1 && j == y1) {
                            //Skip the start point
                            cont = false;
                        }
                        if (i == x2 && j == y2) {
                            //Skip the end point
                            cont = false;
                        }
                    }
                }


                if (cont) {
                    // cell is included in path
                    if (metrics != "Euclidean") {
                        // not euclidean
                        // cell is in path
                        Grid.grid2.add(path_circle, j, i);

                    }
                }
            }
        }


    }


    /**
     * This method
     *
     * @param x1 : user entered starting point 'x' co-ordinate
     * @param y1 : user entered starting point 'y' co-ordinate
     * @param x2 : user entered ending point 'x' co-ordinate
     * @param y2 : user entered ending point 'y' co-ordinate
     */
    public static void findPath(int x1, int y1, int x2, int y2) {

        closed = new boolean[Grid.SIZE][Grid.SIZE];

        // Priority que that has open cells.
        //Lowest final cost takes first.


        open = new PriorityQueue<>(new Comparator<Grid>() {
            @Override
            public int compare(Grid o1, Grid o2) {
                Grid obj1 = o1;
                Grid obj2 = o2;

                if (obj1.fullCost < obj2.fullCost) {
                    return -1; // this matters for priority
                } else if (obj1.fullCost > obj2.fullCost) {
                    return 1;
                } else {
                    return 0; // equal fCosts : then first in first out
                }
            }
        });


        
        for (int x = 0; x < Grid.SIZE; x++) {
            for (int y = 0; y < Grid.SIZE; y++) {

                //Make a cell object which has grid and co-ordinates
                grid[x][y] = new Grid(x, y, Grid.weightArray[x][y]);

                //Calculates the heuristic cost
                if (metrics == "Manhattan") {
                    grid[x][y].h = (Math.abs(x - x2) + Math.abs(y - y2)) * grid[x][y].weight; //calculating manhattan cost

                } else if (metrics == "Euclidean") {


                    grid[x][y].h = (Math.sqrt(Math.pow((x - x2), 2) + Math.pow((y - y2), 2))) * grid[x][y].weight;//calculating euclidean

                } else if (metrics == "Chebyshev") {
                    grid[x][y].h = (Math.max(Math.abs(x - x2), Math.abs(y - y2))) * grid[x][y].weight;//calculating chebyshev

                }
            }
        }



        /**
         * User entered start location starts with 0
         */
        grid[x1][y1].g = 0;


        /**
         * User entered start location full cost
         * F = G + H
         */
        grid[x1][y1].fullCost = grid[x1][y1].g + grid[x1][y1].h;

        // create blocks in the grid
        // @param : {X,Y} coordinates to block
        /*for (int i = 0; i < blocked.length; i++) {
            // for each cell to be blocked

            // block cell : indexed [x][y]
            //setBlocked(blocked[i][0], blocked[i][1]);

            // block in matrix (to draw grid)
            //[blocked[i][0]][blocked[i][1]] = true;
        }*/

        // travel from starting point to ending point
        travel();

        // trace path ///////////////////////////////////////////////////////////
        if (closed[x2][y2]) {

            // start from end point
            Grid current = grid[x2][y2];
            Grid.waypoint.add(new Integer[]{current.x, current.y});

            // iterate until the super parent (starting cell) is found
            while (current.parent_cell != null) {
                // add parent of the current node
                Grid.waypoint.add(new Integer[]{current.parent_cell.x, current.parent_cell.y});

                // ready for next parent finding
                current = current.parent_cell;
            }

            // calculate path - time end
            //ime1_t2 = System.nanoTime();

            System.out.println("");
            System.out.println("Path found!");
            System.out.println("");

        } else {

            // end point has not reached
            // path stopped in middle
            // calculate path - time end
            //time1_t2 = System.nanoTime();

            System.out.println("");
            System.out.println("No possible path!");
            System.out.println("");
        }




        // display grid & path in CLI - time start
        //time2_t1 = System.nanoTime();

        /**
         * Display grid including the shortest path in CLI
         */
        System.out.println("Grid: ");
        for (int x = 0; x < Grid.SIZE; x++) { // x
            for (int y = 0; y < Grid.SIZE; y++) { // y

                boolean cont = false; // "in path arraylist" status

                for (Integer[] iarr : Grid.waypoint) {

                    if (iarr[0] == x && iarr[1] == y) {
                        cont = true;
                    }
                }

                if (x == x1 && y == y1) {
                    System.out.print("S  "); // start point
                } else if (x == x2 && y == y2) {
                    System.out.print("E  ");  // end point
                } else if (cont) {
                    System.out.print("X  "); // travelled through
                } else if (grid[x][y] != null) {
                    System.out.printf("%-3d", 0); // normal open cell
                } else {
                    System.out.print("1  "); // blocked
                }
            }
            System.out.println();
        }
        System.out.println();

        //time2_t2 = System.nanoTime();

    }


    static void travel() {

        //Start location adding to open list
        open.add(grid[Grid.s_X][Grid.s_Y]);

        // Object which holds the current cell
        Grid current;

        while (true) {
            current = open.poll();
            if (current.weight == 5) {
                break;
            }

            // this cell is no more visitable
            closed[current.x][current.y] = true;

            /* end cell is closed in above line,
             since 'path found' is determined if end cell is closed */

            // current cell is the end cell
            // no need to look more
            if (current.equals(grid[Grid.e_X][Grid.e_Y])) {
                return;
            }

            // update costs of connected cells
            // each connected cells are replaced in this
            Grid t;

            if (current.x - 1 >= 0) {

                // left cell
                t = grid[current.x - 1][current.y];
                calculateCost(current, t, current.g + ver_hori_cost);

                if (current.y - 1 >= 0) {

                    if (metrics != "Manhattan") {
                        // left bottom cell (not for manhattan)
                        System.out.println(metrics);
                        t = grid[current.x - 1][current.y - 1];
                        calculateCost(current, t, current.g + dCost);
                    }

                }


                if (current.y + 1 < grid[0].length) {
                    if (metrics != "Manhattan") {
                        // left top cell (not for manhattan)
                        System.out.println(metrics);
                        t = grid[current.x - 1][current.y + 1];
                        calculateCost(current, t, current.g + dCost);
                    }
                }


            }


            if (current.y - 1 >= 0) {
                // bottom cell
                t = grid[current.x][current.y - 1];
                calculateCost(current, t, current.g + ver_hori_cost);
            }


            if (current.y + 1 < grid[0].length) {
                // top cell
                t = grid[current.x][current.y + 1];
                calculateCost(current, t, current.g + ver_hori_cost);
            }


            if (current.x + 1 < grid.length) {
                // right cell
                t = grid[current.x + 1][current.y];
                calculateCost(current, t, current.g + ver_hori_cost);


                if (current.y - 1 >= 0) {
                    if (metrics != "Manhattan") {
                        // right bottom cell (not for manhattan)
                        System.out.println(metrics);
                        t = grid[current.x + 1][current.y - 1];
                        calculateCost(current, t, current.g + dCost);
                    }
                }


                if (current.y + 1 < grid[0].length) {
                    if (metrics != "Manhattan") {
                        // top right cell (not for manhattan)
                        System.out.println(metrics);
                        t = grid[current.x + 1][current.y + 1];
                        calculateCost(current, t, current.g + dCost);
                    }
                }
            }

            //while continues
        }
    }

    static void calculateCost(Grid current, Grid temp, double cost) {

        // if temp cell is blocked or visited through
        if (temp.weight == 5 || closed[temp.x][temp.y]) {
            // no need to look for this cell to move
            return;
        }

        // temporary F value for the temp node
        double tempFinalCost = cost + temp.h; // temp(F = G + H)

        // open status of temp cell
        boolean inOpen = open.contains(temp);

        // if temp cell is not calculated yet
        // or new f cost is less than old f cost
        if (!inOpen || tempFinalCost < temp.fullCost) {
            // update the new F & G costs for the temp cell
            temp.fullCost = tempFinalCost;
            temp.g = cost;

            // current cell is the parent for this temp cell
            temp.parent_cell = current;

            if (!inOpen) {
                // open this cell
                open.add(temp);
            }
        }
    }

}
