package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GUI extends Application {

    //private static  int SIZE = 21;
    private static final int WIDTH = 40;
    Stage Welcome, Panel;
    Scene scn1, scn2, scn3;
    static GridPane grid;
    static ImageView background, gridimg;
    static int [][]weightarray_40;
    static int S_xcor,S_ycor,E_xcor,E_ycor;

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
        RadioButton radio_grid = new RadioButton("Grid");
        RadioButton radio_bckgrnd = new RadioButton("Background");
        radio_grid.setToggleGroup(group);
        radio_bckgrnd.setToggleGroup(group);
        radio_grid.setUserData("Grid");
        radio_bckgrnd.setUserData("Background");


        radio_grid.setSelected(true);

        HBox options = new HBox();
        setContainerHbox(options, Pos.CENTER, 20);
        options.getChildren().addAll(radio_grid, radio_bckgrnd);

        sub2.getChildren().addAll(lblSelectView, options);
        sub_menu.getChildren().addAll(sub1, sub2);


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


        HBox btns = new HBox();
        setContainerHbox(btns, Pos.CENTER, 50);
        Button btnFind = new Button("Find Path");
        Button btnSetPoint = new Button("Set Point");
        btns.getChildren().addAll(btnSetPoint, btnFind);

        Button btnReset = new Button("Reset");
        Button btn_40 = new Button("40X40 Grid");
        Button btn_20=new Button("20X20 Grid");

        subMenu2.getChildren().addAll(S_xyBox, E_xyBox, btns, btnReset, btn_40,btn_20);

        menuWrapper.getChildren().addAll(menuTitle, sub_menu, subMenu2);


        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setId("Grid");
        Grid.setGrid(grid);
        grid.getChildren().addAll(menuWrapper);


        /**
         * This method sets the background image
         *
         */
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                String temp;
                Image img = new Image("/Images/background.png");

                if (group.getSelectedToggle() != null) {
                    System.out.println("Selected View : " + group.getSelectedToggle().getUserData().toString());
                    System.out.println('\n');
                    temp = group.getSelectedToggle().getUserData().toString();//gets the dataset of radion button

                    switch (temp) {
                        case "Background":
                            grid.setStyle("-fx-background-image: url('/Images/background.png')");
                            //grid.setBackground();//getChildren().add(new ImageView(img));
                            break;

                        case "Grid":
                            grid.setStyle("-fx-background-image: null");
                    }
                }

            }
        });


        /**
         * HBox that holds the grid and the menu
         */
        HBox canvas_layout = new HBox();
        setContainerHbox(canvas_layout, Pos.CENTER, 30);
        canvas_layout.getChildren().addAll(menuWrapper, grid);


        /**
         * Button action for find path btn
         * Button action for reset btn
         */
        btnFind.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (Grid.startHistory > 0) {

                        /**
                         * Converts user inputs of x,y co-ordinate to int
                         * S_xcor : Starting point x co-ordinates
                         * S_ycor : Starting point y co-ordinates
                         */
                         S_xcor = Integer.parseInt(xCor.getText().toString());
                         S_ycor = Integer.parseInt(yCor.getText().toString());




                        /**
                         * Converts user inputs of x,y co-ordinate to int
                         * E_xcor : End point x co-ordinates
                         * E_ycor : End point y co-ordinates
                         */
                         E_xcor = Integer.parseInt(xCor_E.getText().toString());
                         E_ycor = Integer.parseInt(yCor_E.getText().toString());

                        //Grid.string_Validation();

                        /**
                         * Set the metrics and show the path in the grid
                         */
                        PathFinder.setMetrics(getChoice(Metrics));//gets the selected metrics and set it
                        Grid.findPath_btnAction(grid, S_xcor, S_ycor, E_xcor, E_ycor);

                    } else {
                        Alert error = Grid.alerts("Error", "Set waypoints first", Alert.AlertType.ERROR, "Fix");
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
                    Alert error = Grid.alerts("Error", "Enter valid waypoints", Alert.AlertType.ERROR, "Fix");
                    error.show();
                    //e.printStackTrace();
                }

            }

        });
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
        btn_40.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    gridDouble();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_20.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    gridSmall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        scn2 = new Scene(canvas_layout);
        scn2.getStylesheets().add("/StyleSheet/SCN2_CSS.css");
        return scn2;
    }


    /**
     * This method returns the selected string value
     * from the choicebox
     *
     * @param cb : selected string value
     * @return
     */
    private String getChoice(ChoiceBox<String> cb) {
        return cb.getValue();
    }


    /**
     * This method contains the properties of
     * the TextField
     *
     * @param txt         : txt field we created in GUI
     * @param placeHolder : placeholder we created
     * @param width       : width of txt field
     */
    private void setTextField(TextField txt, String placeHolder, double width) {
        txt.setPromptText(placeHolder);
        txt.setPrefWidth(width);
    }


    /**
     * This method contains the properties of
     * the HBox
     *
     * @param hBox    : Hbox we created
     * @param value:  Position of the Hbox
     * @param spacing : Spacing of the Hbox
     */
    private void setContainerHbox(HBox hBox, Pos value, double spacing) {
        hBox.setAlignment(value);
        hBox.setSpacing(spacing);
    }


    /**
     * This method contains the properties of
     * the VBox
     *
     * @param vBox    : Vbox we created
     * @param value   : Position of the Vbox
     * @param spacing : Spacing of the Vbox
     */
    private void setContainerVbox(VBox vBox, Pos value, double spacing) {
        vBox.setAlignment(value);
        vBox.setSpacing(spacing);
    }


    /**
     * This method reset the grid
     * @param scene
     */
    private void resetgrid(Scene scene) {
        Grid.startHistory = 0;

        Welcome.setScene(scene);

    }

    public static void gridDouble() {

        Grid.SIZE = 40;

        System.out.println("VImma adarei");

        int[][] weightArray_40 = new int[2*Grid.weightArray.length][2*Grid.weightArray.length];

        for(int i=0; i<Grid.weightArray.length; i++) {
            for(int j=0; j<Grid.weightArray.length; j++) {
                int temp = Grid.weightArray[i][j];
                weightArray_40[i*2][j*2] = temp;
                weightArray_40[i*2][j*2+1] = temp;
                weightArray_40[i*2+1][j*2] = temp;
                weightArray_40[i*2+1][j*2+1] = temp;
            }
        }

        Grid.weightArray = weightArray_40;
        Grid.rectangle_height=15;
        Grid.rectangle_width=15;
        Grid.setGrid(grid);


    }

    public static void gridSmall(){

        weightarray_40=Grid.weightArray;
        Grid.rectangle_height=30;
        Grid.rectangle_width=30;
        Grid.setGrid(grid);
    }
}
