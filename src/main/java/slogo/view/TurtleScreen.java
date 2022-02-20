package slogo.view;


import java.util.ArrayList;
import java.util.List;
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
  private List<GraphicalTurtle> myTurtles = new ArrayList();
  private GraphicalTurtle selectedTurtle;

  public TurtleScreen(int width, int height) {
    myCanvas = new Canvas(width, height);
    System.out.println(this.getClass().getName());
    canvasWidth = width;
    canvasHeight = height;
    myTurtles.add(new GraphicalTurtle(myCanvas, width, height, "defaultTurtle.png", 0));
    selectedTurtle = myTurtles.get(0);
    this.setId("myTurtleScreen");

    this.setColor(DEFAULT_COLOR);
    this.setMaxHeight(height);
    this.setMaxWidth(width);
    double[] turtleCoords = selectedTurtle.getTurtleCoordinates();
    Text coordText = new Text(
        String.format("(%s,%s)", turtleCoords[0], turtleCoords[1]
        ));
    coordText.setX(0);
    coordText.setY(10);
    this.getChildren().addAll(coordText);

    this.getChildren().addAll(myCanvas, selectedTurtle.getTurtleView());

  }


  public void setColor(String newColor) {
    if (checkValidColor(newColor)) {
      Color color = Color.valueOf(newColor);
      String clr = String.valueOf(color);
      clr = transcribeToRGB(clr);
      this.setStyle("-fx-background-color: " + clr);

    }
  }

  private String transcribeToRGB(String clr) {
    clr = clr.substring(2, clr.length() - 2);
    clr = "#" + clr;
    return clr;
  }

  public void moveTurtle(double[] end, double degree) {//add List<Move>
    //for each move in list
    //        get(Move.getTurtleId())
    myTurtles.get(0).drawLine(end);
    myTurtles.get(0).animateTurtle(end, degree);

  }

  public void setInkColor(String color) {
    if (checkValidColor(color)) {
      for (GraphicalTurtle turtle : myTurtles) {
        turtle.setInkColor(color);
      }
    }
  }
  public void setImage(String filepath){
    for (GraphicalTurtle turtle : myTurtles) {
      turtle.setImage(filepath);
    }
  }
  private boolean checkValidColor(String newColor) {
    try {
      Color color = Color.valueOf(newColor);
      return true;
    } catch (IllegalArgumentException e) {
      ErrorWindow errorWindow = new ErrorWindow("Invalid Color Selected");
      return false;
    }
  }
}
