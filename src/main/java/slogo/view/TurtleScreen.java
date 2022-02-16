package slogo.view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TurtleScreen extends Pane {

  private final String DEFAULT_COLOR = "KHAKI";
  private GraphicsContext gc;
  private Canvas myCanvas;
  private double canvasWidth;
  private double canvasHeight;
  private String DEFAULT_RESOURCE_PATH="/";
  public TurtleScreen(int width, int height) {
    myCanvas = new Canvas(width, height);
    gc = myCanvas.getGraphicsContext2D();
    canvasWidth = width;
    canvasHeight = height;
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(2);
    double[] a={0,0};
    double[] b={10,50};
    drawLine(gc,a,b);
    Image image = new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH+"slogo/custom_turtle.png"));
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    this.setColor(Color.valueOf(DEFAULT_COLOR));
    this.setMaxHeight(height);
    this.setMaxWidth(width);
    imageView.setX(width/2-image.getWidth()/2);
    imageView.setY(height/2-image.getHeight()/2);
    this.getChildren().addAll(myCanvas,imageView);

  }
  private void drawLine(GraphicsContext gc, double[] start,double[] end){
    gc.strokeLine(translateXtoCanvasCoordinate(start[0]), translateYtoCanvasCoordinate(start[1]),
        translateXtoCanvasCoordinate(end[0]), translateYtoCanvasCoordinate(end[1]));
  }
  private void setColor(Color color) {
    String clr = String.valueOf(color);
    clr = transcribeToRGB(clr);
    this.setStyle("-fx-background-color: " + clr);

  }

  private String transcribeToRGB(String clr) {
    clr = clr.substring(2, clr.length() - 2);
    clr = "#" + clr;
    return clr;
  }

  private double translateXtoCanvasCoordinate(double x) {
    return canvasWidth / 2 + x;
  }

  private double translateYtoCanvasCoordinate(double y) {
    return canvasHeight / 2 - y;
  }
}
