package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GUI extends Application {

    private static final int SIZE = 21;
    private static final int WIDTH = 40;
    Stage Welcome, Panel;
    Scene scn1, scn2, scn3;
    static GridPane grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Welcome = primaryStage;
        Welcome.setTitle("Path Finder - Developed by Vimukthi");
        Welcome.setMinWidth(950);
        Welcome.setMinHeight(650);


        // layout settings
        GridPane grid1 = new GridPane();
        grid1.setId("Grid");


        //The start button
        Button startbtn = new Button("Start");
        GridPane.setConstraints(startbtn, 3, 3);
        startbtn.setOnAction(event -> Welcome.setScene(scn2));

        grid1.setAlignment(Pos.CENTER);
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.getChildren().addAll(startbtn);


        scn1 = new Scene(grid1, 1024, 720);
        Welcome.setScene(scn1);
        scn1.getStylesheets().add("/StyleSheet/SCN1_CSS.css");
        scn2 = GridPanel();
        Welcome.show();
    }

    /**
     * Grid Panel window that has all the GUI elements in the Path Finder
     */

    private Scene GridPanel() {


        /**
         * Main layout is a HBox which holds two elements
         * 1: VBox - menuWrapper (holds all the menu elements)
         * 2: GridPane - holds the grid
         */


        /**
         * Menu container which holds all the menu elements
         */
        VBox menuWrapper = new VBox();
        setContainerVbox(menuWrapper, Pos.TOP_RIGHT, 50);


        /**
         * Menu title in the window
         */
        HBox menuTitle = new HBox();
        menuTitle.setAlignment(Pos.CENTER);

        Label labelTitle = new Label("MENU");
        labelTitle.setId("MENUTITLE");
        labelTitle.setAlignment(Pos.CENTER);
        menuTitle.getChildren().add(labelTitle);


        /**
         * Sub Menu Vbox
         */
        VBox sub_menu = new VBox();
        setContainerVbox(sub_menu, Pos.CENTER, 50);



        /**
         * Sub Menu first item
         */
        HBox sub1 = new HBox();
        setContainerHbox(sub1, Pos.CENTER, 20);

        Label label_Matrics = new Label("Select Metrics");
        ChoiceBox<String> Metrics = new ChoiceBox<String>();
        //metrics
        Metrics.getItems().addAll("Manhattan", "Euclidean", "Chebyshev");
        //set a default value
        Metrics.setValue("Manhattan");
        sub1.getChildren().addAll(label_Matrics, Metrics);



        /**
         * Sub Menu Second item
         */
        VBox sub2 = new VBox();
        setContainerVbox(sub2, Pos.CENTER, 20);

        Label lblSelectView = new Label("Select View");

        ToggleGroup group = new ToggleGroup();
        RadioButton Normal = new RadioButton("Grid");
        RadioButton Colored = new RadioButton("Background");
        Normal.setToggleGroup(group);
        Colored.setToggleGroup(group);
        Normal.setUserData("Grid");
        Colored.setUserData("Background");

        Normal.setSelected(true);

        HBox options = new HBox();
        setContainerHbox(options, Pos.CENTER, 20);
        options.getChildren().addAll(Normal, Colored);

        sub2.getChildren().addAll(lblSelectView, options);


        sub_menu.getChildren().addAll(sub1,sub2);


        /**
         * Second Sub Menu
         */
        VBox subMenu2 = new VBox();
        setContainerVbox(subMenu2, Pos.CENTER, 20);


        HBox S_xyBox = new HBox();
        setContainerHbox(S_xyBox, Pos.CENTER, 10);


        Label lbl_S_XYCo = new Label("Starting point (x, y)");

        TextField xCor = new TextField();
        setTextField(xCor, "x:", WIDTH);
        TextField yCor = new TextField();
        setTextField(yCor, "y:", WIDTH);

        S_xyBox.getChildren().addAll(lbl_S_XYCo, xCor, yCor);


        Label lbl_E_XYCo = new Label("Ending point (x, y)  ");

        TextField xCor_E = new TextField();
        setTextField(xCor_E, "x:", WIDTH);
        TextField yCor_E = new TextField();
        setTextField(yCor_E, "y:", WIDTH);


        HBox E_xyBox = new HBox();
        setContainerHbox(E_xyBox, Pos.CENTER, 10);
        E_xyBox.getChildren().addAll(lbl_E_XYCo, xCor_E, yCor_E);


        HBox btns=new HBox();
        setContainerHbox(btns,Pos.CENTER,50);
        Button btnFind = new Button("Find Path");
        Button btnSetPoint=new Button("Set Point");
        btns.getChildren().addAll(btnSetPoint,btnFind);

        Button btnReset = new Button("Reset");

        subMenu2.getChildren().addAll(S_xyBox, E_xyBox, btns, btnReset);

        menuWrapper.getChildren().addAll(menuTitle, sub_menu, subMenu2);





        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setId("Grid");
        Grid.setGrid(grid);
        grid.getChildren().addAll(menuWrapper);


        /**
         * HBox that holds the grid and the menu
         */

        ///////xxxxxxxxxxxxxxxxxxxxxx/////////////
        HBox canvas_layout = new HBox();
        setContainerHbox(canvas_layout, Pos.CENTER, 30);
        ///////xxxxxxxxxxxxxxxxxxxxxx/////////////

        canvas_layout.getChildren().addAll(menuWrapper, grid);


        /**
         * Button action for find path btn
         * Button action for reset btn
         */
        btnFind.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {

                    if (Grid.startHistory>0){

                        /**
                         * Converts user inputs of x,y co-ordinate to int
                         * S_xcor : Starting point x co-ordinates
                         * S_ycor : Starting point y co-ordinates
                         */
                        int S_xcor = Integer.parseInt(xCor.getText().toString());
                        int S_ycor = Integer.parseInt(yCor.getText().toString());


                        /**
                         * Converts user inputs of x,y co-ordinate to int
                         * E_xcor : End point x co-ordinates
                         * E_ycor : End point y co-ordinates
                         */
                        int E_xcor = Integer.parseInt(xCor_E.getText().toString());
                        int E_ycor = Integer.parseInt(yCor_E.getText().toString());


                        /**
                         * Set the metrics and show the path in the grid
                         */
                        PathFinder.setMetrics(getChoice(Metrics));//gets the selected metrics and set it
                        Grid.findPath_btnAction(grid, S_xcor, S_ycor, E_xcor, E_ycor);

                    }else{
                        Alert error=Grid.alerts("Error","Set waypoints first",Alert.AlertType.ERROR,"Fix");
                        error.show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        btnSetPoint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {

                    /**
                     * Converts user inputs of x,y co-ordinate to int
                     * S_xcor : Starting point x co-ordinates
                     * S_ycor : Starting point y co-ordinates
                     */
                    int S_xcor = Integer.parseInt(xCor.getText().toString());
                    int S_ycor = Integer.parseInt(yCor.getText().toString());


                    /**
                     * Converts user inputs of x,y co-ordinate to int
                     * E_xcor : End point x co-ordinates
                     * E_ycor : End point y co-ordinates
                     */
                    int E_xcor = Integer.parseInt(xCor_E.getText().toString());
                    int E_ycor = Integer.parseInt(yCor_E.getText().toString());


                    /**
                     * Set the metrics and show the path in the grid
                     */
                    PathFinder.setMetrics(getChoice(Metrics));//gets the selected metrics and set it
                    Grid.show_Start_End(grid, S_xcor, S_ycor, E_xcor, E_ycor);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });



        scn2 = new Scene(canvas_layout);
        scn2.getStylesheets().add("/StyleSheet/SCN2_CSS.css");

        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    resetgrid(scn2);
                    System.out.println("reset");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return scn2;
    }

    private String getChoice(ChoiceBox<String> cb) {
        return cb.getValue();
    }

    private void setTextField(TextField txt, String placeHolder, double width) {
        txt.setPromptText(placeHolder);
        txt.setPrefWidth(width);
    }

    private void setContainerHbox(HBox hBox, Pos value, double spacing) {
        hBox.setAlignment(value);
        hBox.setSpacing(spacing);
    }

    private void setContainerVbox(VBox vBox, Pos value, double spacing) {
        vBox.setAlignment(value);
        vBox.setSpacing(spacing);
    }

    private void resetgrid(Scene scene) {
        Grid.startHistory=0;
        Welcome.setScene(scene);

    }


}
