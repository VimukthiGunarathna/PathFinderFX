package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Grid {


    /**
     * G:distance from starting point
     * H:distance to end point (approximate)
     */
    static final int SIZE = 20;
    int x, y;//x,y coordinates of the grid
    double weight;
    double g;//g value
    double h;//h value
    double fullCost; // F = G + H
    Grid parent_cell; // cell, visited before the current cell

    // contains cells that were travelled from
    static ArrayList<Integer[]> waypoint = new ArrayList<Integer[]>();
    static GridPane grid2;
    static final int radius = 10;
    static int s_X, s_Y, e_X, e_Y;
    static int startHistory = 0;

    public Grid(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    /**
     * Two Dimensional array which holds the weight of
     * each and every block in the cell
     * <p>
     * 1 : White cells
     * 2: Light gray cells
     * 3: Gray cells
     * 4: Dark gray cells
     */

    public static int[][] weightArray = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 1, 4, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 4, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 1, 1, 2, 2, 1, 1},
            {1, 4, 4, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 1},
            {4, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1},
            {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4},
            {1, 1, 2, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4},
            {1, 2, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1},
            {1, 2, 3, 2, 2, 2, 3, 2, 4, 1, 1, 1, 4, 4, 4, 4, 2, 1, 1, 1},
            {1, 2, 2, 1, 1, 1, 4, 4, 4, 4, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1},
            {1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 5},
            {1, 1, 4, 4, 4, 4, 1, 1, 1, 2, 2, 5, 5, 1, 1, 1, 1, 1, 1, 5},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 5, 5, 5, 1, 1, 5, 5, 5},
            {1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},
            {2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5},

    };


    public static void setGrid(GridPane grid) {

        Rectangle rectangle;

        grid.setGridLinesVisible(true); //Shows the rulers


        /**
         * Sets the X co-ordinates in the grid
         *
         */
        /*for (int i = 1; i < SIZE; i++) {
            Label labelX = new Label("x" + i);
            grid.setHalignment(labelX, HPos.CENTER);
            grid.add(labelX, 0, i);
        }*/

        /**
         * Sets the Y co-ordinates in the grid
         */
        /*for (int j = 1; j < SIZE; j++) {
            Label labelY = new Label("y" + j);
            grid.setHalignment(labelY, HPos.CENTER);
            grid.add(labelY, j, 0);
        }*/


        /**
         * i : outer loop - Rows
         * j : inner loop - Columns
         *
         * Sets the rectangles on the grid
         */

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int weight = Grid.weightArray[i][j];
                rectangle = new Rectangle(0, 0, 30, 30);

                switch (weight) {
                    case 1:
                        rectangle.setFill(Color.rgb(255, 255, 255, 0.6));
                        break;

                    case 2:
                        rectangle.setFill(Color.rgb(185, 185, 185, 0.6));
                        break;

                    case 3:
                        rectangle.setFill(Color.rgb(125, 125, 125, 0.6));
                        break;

                    case 4:
                        rectangle.setFill(Color.rgb(65, 65, 65, 0.6));
                        break;

                    case 5:
                        rectangle.setFill(Color.rgb(0, 0, 0, 0.8));
                        break;
                }
                grid.add(rectangle, j, i);
            }
        }
    }

    public static void show_Start_End(GridPane grid, int s_x, int s_y, int e_x, int e_y) {

        grid2 = grid;

        s_X = s_x;
        s_Y = s_y;
        e_X = e_x;
        e_Y = e_y;

        System.out.println("~~~ Starting Point ~~~");
        System.out.println("X :" + s_X);
        System.out.println("Y :" + s_Y);
        System.out.println('\n');

        System.out.println("~~~ End Point ~~~");
        System.out.println("X :" + e_X);
        System.out.println("Y :" + e_Y);

        boolean cont = validatePoints(s_X, s_Y, e_X, e_Y);

        if (cont) {
            Circle start = new Circle(radius, Color.GREEN); //A : starting point
            Circle end = new Circle(radius, Color.RED);   //B : ending point
            grid2.add(start, s_Y, s_X);
            grid2.add(end, e_Y, e_X);
            startHistory += 1;
        } else {
            Alert error = alerts("Error", "Enter valid co-ordinates", Alert.AlertType.ERROR, "Fix");
            error.show();
            startHistory = 0;
        }


    }

    public static void findPath_btnAction(GridPane grid, int S_xcor, int S_ycor, int E_xcor, int E_ycor) {


        PathFinder.findPath(S_xcor, S_ycor, E_xcor, E_ycor);
        PathFinder.drawPath(S_xcor, S_ycor, E_xcor, E_ycor, waypoint);


    }

    public static Grid[][] getGrid() {

        Grid[][] grid = new Grid[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Grid(i, j, weightArray[i][j]);
            }
        }
        return grid;
    }


    /**
     * This is validation method to check whether user has
     * entered correct co-ordinates
     *
     * @param sx : user entered starting point 'x' co-ordinate
     * @param sy : user entered starting point 'y' co-ordinate
     * @param ex : user entered ending point 'x' co-ordinate
     * @param ey : user entered ending point 'y' co-ordinate
     * @return : returns a boolean value
     */
    public static boolean validatePoints(int sx, int sy, int ex, int ey) {

        boolean cont = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                int weight = Grid.weightArray[i][j];
                if (sx == i && sy == j) {
                    if (weight == 5) {
                        System.out.println("fuck");
                        cont = false;
                    } else {
                        cont = true;
                    }
                    if (ex == i && ey == j) {
                        if (weight == 5) {
                            System.out.println("fuck");
                            cont = false;
                        } else {
                            cont = true;
                        }
                    }
                }

            }
        }
        return cont;
    }


    /**
     * A method to create alert box
     * Takes the values in the parameter and returns the alert
     */
    public static Alert alerts(String title, String Content, Alert.AlertType type, String Header) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);

        return alert;
    }

}
