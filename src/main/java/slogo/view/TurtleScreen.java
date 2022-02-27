package slogo.view;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.SequentialTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import slogo.model.MoveInfo;

public class TurtleScreen extends Pane {

  private final String DEFAULT_COLOR = "KHAKI";
  private GraphicsContext gc;
  private Canvas myCanvas;
  private double canvasWidth;
  private double canvasHeight;
  private List<GraphicalTurtle> myTurtles = new ArrayList();
  private GraphicalTurtle selectedTurtle;
  private SequentialTransition animationSequence = new SequentialTransition();
  private double lastHeading = 0;

  public TurtleScreen(int width, int height) {
    myCanvas = new Canvas(width, height);
    canvasWidth = width;
    canvasHeight = height;
    myTurtles.add(new GraphicalTurtle(myCanvas, width, height, "defaultTurtle.png", 0));
    selectedTurtle = myTurtles.get(0);
    this.setId("myTurtleScreen");
    Circle center = new Circle(width / 2, height / 2, 3);
    center.setFill(Color.CRIMSON);
    this.setColor(DEFAULT_COLOR);
    this.setMaxHeight(height);
    this.setMaxWidth(width);
    double[] turtleCoords = selectedTurtle.getTurtleCoordinates();
    Text coordText = new Text(
        String.format("(%s,%s)", turtleCoords[0], turtleCoords[1]
        ));
    coordText.setX(0);
    coordText.setY(10);
    this.getChildren().addAll(coordText, center);

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

  public void moveTurtle(List<MoveInfo> moves) {
    animationSequence.getChildren().clear();
    for (MoveInfo move : moves) {


      //        get(Move.getTurtleId())
      double[] end = {move.getEnd().getX(), move.getEnd().getY()};
      double[] start = {move.getStart().getX(), move.getStart().getY()};
      if (move.getHeading() != lastHeading) {
        lastHeading=move.getHeading();
        animationSequence.getChildren()
            .add(myTurtles.get(0).makeRotateAnimation(move.getHeading()));
      }
      if (!Arrays.equals(start,end)) {
        animationSequence.getChildren()
            .add(myTurtles.get(0).makeMovementAnimation(start, end, true));
      }


    }
    animationSequence.play();
  }

  public void setInkColor(String color) {
    if (checkValidColor(color)) {
      for (GraphicalTurtle turtle : myTurtles) {
        turtle.setInkColor(color);
      }
    }
  }

  public void setImage(String filepath) {
    for (GraphicalTurtle turtle : myTurtles) {
      turtle.changeImage(filepath);
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

  String getCurrentColor() {
    return this.getStyle();
  }

  Paint getTurtleInkColor() {

    return myTurtles.get(0).getInkColor();
  }

  String getTurtleDesign() {
    return myTurtles.get(0).getLastUsedFile();
  }

  double[] getTurtleCurrentPos() {
    return myTurtles.get(0).getTurtleCoordinates();
  }

  double getTurtleCurrentRotate() {
    return myTurtles.get(0).getTurtleRotate();
  }

  int getTurtleDrawnLineCount() {
    return myTurtles.get(0).getLineCount();
  }
}
