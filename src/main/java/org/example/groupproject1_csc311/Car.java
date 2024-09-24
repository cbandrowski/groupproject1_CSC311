package org.example.groupproject1_csc311;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Title:Car Object class
 * Description: Car object class used to
 * load images for cars or draw cars to be used in maze
 * @Authors Cody Bandrowski
 */
public class Car {
    //Car's Current Position
    private double x;
    private double y;
    private Group carGroup;



    //Maze image to check for touching walls
    private Image mazeImage;

    public Car(double startX, double startY, Image mazeImage,String carImagePath, boolean useDrawing) throws IOException {
        this.y = startY;
        this.x = startX;
        this.mazeImage = mazeImage;
        carGroup = new Group();

        if (useDrawing) {
            //load car image if true
            createCar(carImagePath);
        } else
            //draw car if false
            drawCar();
    }
    private void createCar(String carImagePath) throws IOException {
        //Load car
        FileInputStream carInput= new FileInputStream(carImagePath);
        Image carImage = new Image(carInput);
        ImageView carView = new ImageView(carImage);

      carView.setFitHeight(40);
      carView.setFitWidth(40);
      carGroup.getChildren().addAll(carView);

    }
    private void drawCar() {
        Rectangle body = new Rectangle(40,20);
        body.setFill(Color.RED);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(2);

        Polygon roof = new Polygon();
        roof.getPoints().addAll(5.0,0.0, //Top left
                35.0, 0.0, //Top right
                30.0, -15.0,//Bottom right
                10.0,-15.0//Bottom left
        );
        roof.setFill(Color.DARKRED);

        Circle frontW = new Circle(10,30,10);
        frontW.setFill(Color.BLACK);
        Circle backW= new Circle(30,30,10);
        backW.setFill(Color.BLACK);

        carGroup.getChildren().addAll(body,roof, frontW, backW);
    }

    public Group getCarGroup(){
        return carGroup;
    }

    public void moveTo(double newX, double newY){
        if(canMove(newX, newY)){
            this.y = newY;
            this.x = newX;
            carGroup.setLayoutX(this.x);
            carGroup.setLayoutY(this.y);
        }
    }
    private boolean canMove( double x, double y){
        //get the pixel reader from maze image
        PixelReader pixel = mazeImage.getPixelReader();

        //Get pixel color ar robots position
        Color colorPos = pixel.getColor((int) x, (int) y);

        Color pathColor = Color.WHITE;
        Color wallColor = Color.BLUE;

        return colorPos.equals(pathColor);


    }
}
