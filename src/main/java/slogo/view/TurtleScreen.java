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

    canvasWidth = width;
    canvasHeight = height;
    GraphicalTurtle gt = new GraphicalTurtle(myCanvas, width, height, "test.png", 0);
    double[] a = {0, 0};
    double[] b = {10, 50};
    gt.drawLine(a, b);

    this.setColor(Color.valueOf(DEFAULT_COLOR));
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


}
