package slogo.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphicalTurtle {

  private GraphicsContext myGraphicsContext;
  private int turtleID;
  private Image myImage;
  private int SCREEN_WIDTH;
  private int SCREEN_HEIGHT;
  private ImageView myImageView;
  private double[] turtleXCoordinate = {0,0};
  private final String DEFAULT_RESOURCE_PATH = "/slogo/";
  private final String DEFAULT_FILENAME = "custom_turtle.png";
  private final int DEFAULT_STROKE = 2;
  private final Paint DEFAULT_INK_COLOR = Color.BLUE;

  public GraphicalTurtle(Canvas turtleScreen, int width, int height, String fileName, int id) {
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
    setImage(fileName);
    turtleID = id;
    myGraphicsContext = turtleScreen.getGraphicsContext2D();
    myGraphicsContext.setLineWidth(DEFAULT_STROKE);
    myGraphicsContext.setStroke(DEFAULT_INK_COLOR);
  }

  public void setImage(String fileName) {
    try {
      myImage = new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH + fileName));
      myImageView = new ImageView();
      myImageView.setImage(myImage);
      myImageView.setX(SCREEN_WIDTH / 2.0 - myImage.getWidth() / 2.0);
      myImageView.setY(SCREEN_HEIGHT / 2.0 - myImage.getHeight() / 2.0);
    } catch (NullPointerException e) {
      setImage(DEFAULT_FILENAME);
      e.printStackTrace();
    }
  }

  public void drawLine(double[] start, double[] end) {
    myGraphicsContext.strokeLine(translateXtoCanvasCoordinate(start[0]),
        translateYtoCanvasCoordinate(start[1]), translateXtoCanvasCoordinate(end[0]),
        translateYtoCanvasCoordinate(end[1]));
    turtleXCoordinate=end;
  }

  private double translateXtoCanvasCoordinate(double x) {
    return SCREEN_WIDTH / 2 + x;
  }

  private double translateYtoCanvasCoordinate(double y) {
    return SCREEN_HEIGHT / 2 - y;
  }

  public ImageView getTurtleView() {
    return myImageView;
  }

  public double[] getTurtleCoordinates() {
return turtleXCoordinate;
  }

}
