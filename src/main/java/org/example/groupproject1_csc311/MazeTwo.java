package org.example.groupproject1_csc311;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Title:Maze With tabs
 * USing tab to switch between two different mazes
 * Underneath code with tab pane is the code without tab pane for Maze 2
 * I can not get Tab to function correctly
 * I can move car on initial load
 * as I click on new tab movement stops and isn't able to happen
 * @Author Cody Bandrowski
 *
 */

public class MazeTwo extends Application {

    // Movement flags
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // Cars for the two mazes
    private Car car1;
    private Car car2;

    // Images for the two mazes
    private Image mazeImage1;
    private Image mazeImage2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the images for the two mazes
        String filePathMaze1 = "src/main/resources/maze.png";
        String filePathMaze2 = "src/main/resources/maze2.png";
        String filePathCar = "src/main/resources/robot.png";

        // Maze 1 image
        FileInputStream mazeInput1 = new FileInputStream(filePathMaze1);
        mazeImage1 = new Image(mazeInput1);
        ImageView mazeView1 = new ImageView(mazeImage1);

        // Maze 2 image
        FileInputStream mazeInput2 = new FileInputStream(filePathMaze2);
        mazeImage2 = new Image(mazeInput2);
        ImageView mazeView2 = new ImageView(mazeImage2);

        // Car object for maze 1
        car1 = new Car(20, 260, mazeImage1, filePathCar, false);

        // Car object for maze 2
        car2 = new Car(20, 260, mazeImage2, filePathCar, false);

        // Pane for maze 1
        Pane root1 = new Pane();
        root1.getChildren().addAll(mazeView1, car1.getCarGroup());

        // Pane for maze 2
        Pane root2 = new Pane();
        root2.getChildren().addAll(mazeView2, car2.getCarGroup());

        // Create a TabPane to hold two tabs (one for each maze)
        TabPane tabPane = new TabPane();

        // Tab for maze 1
        Tab tab1 = new Tab("Maze 1", root1);
        tab1.setClosable(false);  // Prevent closing the tab

        // Tab for maze 2
        Tab tab2 = new Tab("Maze 2", root2);
        tab2.setClosable(false);  // Prevent closing the tab


        // Scene with TabPane
        Scene scene = new Scene(tabPane, 800, 700);
        // Add the tabs to the TabPane
        tabPane.getTabs().addAll(tab1, tab2);

// Prevent TabPane from handling arrow key events for switching tabs
        tabPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN
                    || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                event.consume(); // Prevent TabPane from switching tabs with arrow keys
            }
        });
// Ensure focus is returned to the correct pane when switching tabs
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tab1) {
                // Request focus for the first maze's root pane and the scene
                root1.requestFocus();
                primaryStage.getScene().getRoot().requestFocus();  // Force focus on the scene to capture key events
            } else if (newTab == tab2) {
                // Request focus for the second maze's root pane and the scene
                root2.requestFocus();
                primaryStage.getScene().getRoot().requestFocus();  // Force focus on the scene to capture key events
            }
        });


// Set the correct initial focus for the first maze
        root1.requestFocus();

        // Key pressed and released events for car movement
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    upPressed = true;
                    break;
                case DOWN:
                    downPressed = true;
                    break;
                case LEFT:
                    leftPressed = true;
                    break;
                case RIGHT:
                    rightPressed = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    upPressed = false;
                    break;
                case DOWN:
                    downPressed = false;
                    break;
                case LEFT:
                    leftPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
                    break;
            }
        });

        // Timeline for car movement
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (tabPane.getSelectionModel().getSelectedItem() == tab1) {
                if (upPressed) {
                    car1.moveTo(car1.getCarGroup().getLayoutX(), car1.getCarGroup().getLayoutY() - 10);
                    car1.getCarGroup().setRotate(270);
                }
                if (downPressed) {
                    car1.moveTo(car1.getCarGroup().getLayoutX(), car1.getCarGroup().getLayoutY() + 10);
                    car1.getCarGroup().setRotate(90);
                }
                if (leftPressed) {
                    car1.moveTo(car1.getCarGroup().getLayoutX() - 10, car1.getCarGroup().getLayoutY());
                    car1.getCarGroup().setRotate(180);
                }
                if (rightPressed) {
                    car1.moveTo(car1.getCarGroup().getLayoutX() + 10, car1.getCarGroup().getLayoutY());
                    car1.getCarGroup().setRotate(0);
                }
            } else if (tabPane.getSelectionModel().getSelectedItem() == tab2) {
                if (upPressed) {
                    car2.moveTo(car2.getCarGroup().getLayoutX(), car2.getCarGroup().getLayoutY() - 10);
                    car2.getCarGroup().setRotate(270);
                }
                if (downPressed) {
                    car2.moveTo(car2.getCarGroup().getLayoutX(), car2.getCarGroup().getLayoutY() + 10);
                    car2.getCarGroup().setRotate(90);
                }
                if (leftPressed) {
                    car2.moveTo(car2.getCarGroup().getLayoutX() - 10, car2.getCarGroup().getLayoutY());
                    car2.getCarGroup().setRotate(180);
                }
                if (rightPressed) {
                    car2.moveTo(car2.getCarGroup().getLayoutX() + 10, car2.getCarGroup().getLayoutY());
                    car2.getCarGroup().setRotate(0);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



        primaryStage.setTitle("Maze 1 and Maze 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


/*

Without TabPane

package org.example.groupproject1_csc311;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
public class MazeTwo extends Application {

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private Car car;
    private Image mazeImage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        String filePathMaze= "src/main/resources/maze2.png";
        String filePathCar = "src/main/resources/robot.png";

        FileInputStream mazeInput= new FileInputStream(filePathMaze);
        mazeImage = new Image(mazeInput);
        ImageView mazeView = new ImageView(mazeImage);

        Boolean useDrawing = false;

        car = new Car(20,260,mazeImage,filePathCar,useDrawing);
        //car.carSize(10,10);
         Pane root = new Pane();
         root.getChildren().addAll(mazeView, car.getCarGroup());

         Scene scene = new Scene(root,800,700);

         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),event->{
             if(upPressed) {
                 car.moveTo(car.getCarGroup().getLayoutX(),car.getCarGroup().getLayoutY() - 10);
                 car.getCarGroup().setRotate(270);
             }
             if(downPressed) {
                car.moveTo(car.getCarGroup().getLayoutX(),car.getCarGroup().getLayoutY() + 10);
                car.getCarGroup().setRotate(90);
             }
             if(leftPressed) {
                 car.moveTo(car.getCarGroup().getLayoutX() -10,car.getCarGroup().getLayoutY() );
                 car.getCarGroup().setRotate(180);
             }
             if(rightPressed) {
                 car.moveTo(car.getCarGroup().getLayoutX() + 10,car.getCarGroup().getLayoutY());
                 car.getCarGroup().setRotate(0);
             }
         }));
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();

         scene.setOnKeyPressed(event -> {
             switch (event.getCode()) {
                 case UP:
                     upPressed = true;
                     break;
                     case DOWN:
                         downPressed = true;
                         break;
                         case LEFT:
                             leftPressed = true;
                             break;
                             case RIGHT:
                                 rightPressed = true;
                                 break;
             }
         });

         scene.setOnKeyReleased(event -> {
             switch (event.getCode()) {
                 case UP:
                     upPressed = false;
                     break;
                     case DOWN:
                         downPressed = false;
                         break;
                         case LEFT:
                             leftPressed = false;
                             break;
                             case RIGHT:
                                 rightPressed = false;
                                 break;
             }
         });

        primaryStage.setTitle("Maze 2 ");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
*/