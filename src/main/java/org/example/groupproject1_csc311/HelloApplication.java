package org.example.groupproject1_csc311;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.PixelReader;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.paint.Color;

/**
 * Title: Group Project 1
 * Description: Did not use Car object class for step 1 step 2 and forwards I used CAr object
 * @author Cody Bandrowski
 */
public class HelloApplication extends Application {

    //Robot start coordinates
    private double robotX = 20;
    private double robotY = 260;

    private ImageView robotView;
    private Image mazeImage;
    @Override
    public void start(Stage stage) throws IOException {
        //Loading the image maze
        FileInputStream input = new FileInputStream("src/main/resources/maze.png");
        mazeImage = new Image(input);
        ImageView mazeView = new ImageView(mazeImage);

        //loading robot
        FileInputStream robotInput = new FileInputStream("src/main/resources/robot.png");
        Image robotImage = new Image(robotInput);
        robotView = new ImageView(robotImage);

        //Starting position of Robot
        robotView.setX(robotX);
        robotView.setY(robotY);

        //Pane to hold both the maze and robot
        Pane root = new Pane();
        root.getChildren().addAll(mazeView, robotView);

        //Scene created
        Scene scene = new Scene(root, 800, 700);

        scene.setOnKeyPressed(this::moveRobot);
        /*
        Just movement with no detection
        //Starting the action of movment
        scene.setOnKeyPressed(KeyEvent  -> {
            if (KeyEvent.getCode() == KeyCode.UP) {
                robotY-=10; //Moving UP
            } else if (KeyEvent.getCode() == KeyCode.DOWN) {
                robotY+=10;
            }
            else if (KeyEvent.getCode() == KeyCode.LEFT) {
                robotX-=10;
            }
            else if (KeyEvent.getCode() == KeyCode.RIGHT) {
                robotX+=10;
            }
            robotView.setY(robotY);
            robotView.setX(robotX);
        });
*/

        stage.setTitle("Group Project 1  ");
        stage.setScene(scene);
        stage.show();
    }
    /*
        MoveRobot
        function to move the robot
     */

    private void moveRobot(KeyEvent event){
        double newX = robotX;
        double newY = robotY;

        //Movement using Switch case
        switch (event.getCode()) {
            case UP:
                newY-=10;
                break;
                case DOWN:
                    newY+=10;
                    break;
                    case LEFT:
                        newX-=10;
                        break;
                        case RIGHT:
                            newX+=10;
                            break;
        }

        //Check ig robot can move with new position
        if(canMove(newX, newY)){
            robotX = newX;
            robotY = newY;

            //Update Robot position
            robotView.setX(robotX);
            robotView.setY(robotY);

        }

    }
    /*
        CanMove
        Function to check if the robot is able to move

     */
    private boolean canMove(double x, double y){
        //get the pixel reader from maze image
        PixelReader pixel = mazeImage.getPixelReader();

        //Get pixel color ar robots position
        Color colorPos = pixel.getColor((int) x, (int) y);

        Color pathColor = Color.WHITE;
        Color wallColor = Color.BLUE;

        return colorPos.equals(pathColor);


    }


    public static void main(String[] args) {
        launch();
    }
}