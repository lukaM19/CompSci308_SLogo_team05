package slogo.view;

import java.util.PrimitiveIterator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;

public class GraphicalTurtle {

  private final GraphicsContext myGraphicsContext;
  private int turtleID;
  private String lastUsedFile;
  private Image myImage;
  private int SCREEN_WIDTH;
  private int SCREEN_HEIGHT;
  private ImageView myImageView=new ImageView();
  private double[] TURTLE_INITIAL_POSITION= {0,0};
  private double[] turtleXCoordinate = TURTLE_INITIAL_POSITION;
  private final String DEFAULT_RESOURCE_PATH = "/slogo/";
  private final String DEFAULT_FILENAME = "defaultTurtle.png";
  private final int DEFAULT_STROKE = 2;
  private final Paint DEFAULT_INK_COLOR = Color.BLUE;
  private Rotate rotation = new Rotate();
  private int drawnLinesCount=0;

  public GraphicalTurtle(Canvas turtleScreen, int width, int height, String fileName, int id) {
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
    setImage(fileName);
    myImageView.setId("turtle"+id);
    turtleID = id;
    myGraphicsContext = turtleScreen.getGraphicsContext2D();
    myGraphicsContext.setLineWidth(DEFAULT_STROKE);
    myGraphicsContext.setStroke(DEFAULT_INK_COLOR);

  }

  private void setImage(String fileName) {

      changeImage(fileName);
      myImageView.setX(translateXtoCanvasCoordinate(turtleXCoordinate[0]) - myImage.getWidth() / 2.0);
      myImageView.setY(translateYtoCanvasCoordinate(turtleXCoordinate[1]) - myImage.getHeight() );

      myImageView.getTransforms().add(rotation);

  }

  public void changeImage(String fileName) {
    try{
    myImage = new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH + fileName));
    myImageView.setImage(myImage);
    lastUsedFile=fileName;
  } catch (NullPointerException e) {
    setImage(DEFAULT_FILENAME);
    ErrorWindow err = new ErrorWindow("Invalid Image Filepath");
  }
  }

  public void drawLine(double[] end) {
    double[] start={turtleXCoordinate[0],turtleXCoordinate[1]};
    myGraphicsContext.strokeLine(translateXtoCanvasCoordinate(start[0]),
        translateYtoCanvasCoordinate(start[1]), translateXtoCanvasCoordinate(end[0]),
        translateYtoCanvasCoordinate(end[1]));
    turtleXCoordinate=end;
    drawnLinesCount++;
  }

  private double translateXtoCanvasCoordinate(double x) {
    return SCREEN_WIDTH / 2.0 + x;
  }

  private double translateYtoCanvasCoordinate(double y) {
    return SCREEN_HEIGHT / 2.0 - y;
  }

  public ImageView getTurtleView() {
    return myImageView;
  }

  public double[] getTurtleCoordinates() {
return turtleXCoordinate;
  }
  public void animateTurtle(double[] end,double degree){

    myImageView.setX(translateXtoCanvasCoordinate(end[0])- myImage.getWidth() / 2.0);
    myImageView.setY(translateYtoCanvasCoordinate(end[1])- myImage.getHeight());

    setRotate(end,degree);
    turtleXCoordinate=end;
  }
  private void setRotate(double[] end,double degree){
    rotation.setPivotX(translateXtoCanvasCoordinate(end[0]));
    rotation.setPivotY(translateYtoCanvasCoordinate(end[1]));
    rotation.setAngle(rotation.getAngle() + degree);

  }

  public void setInkColor(String color){
    myGraphicsContext.setStroke(Color.valueOf(color));
  }

  public Paint getInkColor(){return myGraphicsContext.getStroke();}

  public String getLastUsedFile(){return lastUsedFile;}

  public int getLineCount(){return drawnLinesCount;}

  public double getTurtleRotate(){return rotation.getAngle();}
}
