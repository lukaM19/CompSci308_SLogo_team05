package slogo.view;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TurtleScreen extends Pane {

  private final String DEFAULT_COLOR = "KHAKI";
  private GraphicsContext gc;
  private Canvas myCanvas;
  private double canvasWidth;
  private double canvasHeight;

  public TurtleScreen(int width, int height) {
    myCanvas = new Canvas(width, height);
    System.out.println(this.getClass().getName());
    canvasWidth = width;
    canvasHeight = height;
    GraphicalTurtle gt = new GraphicalTurtle(myCanvas, width, height, "custom_turtle.png", 0);
    this.setId("myTurtleScreen");
    double[] a = {0, 0};
    double[] b = {10, 50};
    gt.drawLine(a, b);

    this.setColor(DEFAULT_COLOR);
    this.setMaxHeight(height);
    this.setMaxWidth(width);
    double[] turtleCoords = gt.getTurtleCoordinates();
    Text coordText = new Text(
        String.format("(%s,%s)", turtleCoords[0], turtleCoords[1]
        ));
    coordText.setX(0);
    coordText.setY(10);
    this.getChildren().addAll(coordText);

    this.getChildren().addAll(myCanvas, gt.getTurtleView());

  }


  public void setColor(String newColor) {
    Color color=Color.valueOf(newColor);
    String clr = String.valueOf(color);
    clr = transcribeToRGB(clr);
    this.setStyle("-fx-background-color: " + clr);

  }

  private String transcribeToRGB(String clr) {
    clr = clr.substring(2, clr.length() - 2);
    clr = "#" + clr;
    return clr;
  }


}
