package slogo.view;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.animation.SequentialTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import slogo.model.MoveInfo;

/**
 * A class which creates the Canvas on which the turtle moves and draws. It also manages actions
 * associated with animating turtle commands in order.Depends on JavaFX, info from model passed
 * through the controller.
 *
 * @author Luka Mdivani
 */
public class TurtleScreen extends Pane {

  private static final String DEFAULT_COLOR = "KHAKI";
  private static final String DEFAULT_TURTLE = "defaultTurtle.png";
  private final Canvas myCanvas;
  private Map<Double,GraphicalTurtle> myTurtles = new HashMap<>();
  private GraphicalTurtle selectedTurtle;
  private SequentialTransition animationSequence = new SequentialTransition();
  private double lastHeading = 0;
  private Text posText;
  private final ResourceBundle myResources;
  private final ResourceBundle myErrorResources;
  private static final int positionLabelX = 0;
  private static final int positionLabelY = 15;
  private static final int positionButtonX = 0;
  private static final int positionButtonY = 20;
  private static final double SLIDER_MIN = 0.1;
  private static final double SLIDER_MAX = 10;
  private static final double SLIDER_START = 1;
  private static final double SLIDER_WIDTH = 70;
  private Consumer<GraphicalTurtle> turtleSelector;


  /**
   * The constructor of the class, initializes the canvas with one turtle. adds everything to the
   * root pane.
   *
   * @param width          width of the canvas
   * @param height         height of the canvas
   * @param resourceBundle resource bundle with the string info
   * @param errorResources resource bundle with the error info
   */
  public TurtleScreen(int width, int height, ResourceBundle resourceBundle,
      ResourceBundle errorResources) {
    myResources = resourceBundle;
    myErrorResources = errorResources;
    myCanvas = new Canvas(width, height);
    turtleSelector = this::setSelectedTurtle;
    createTurtle(width, height, 0);
    createTurtle(width, height, 1);
    selectedTurtle = myTurtles.get(0.0);
    this.setId("myTurtleScreen");
    try {
      this.setColor(myResources.getString("defaultCanvasColor"));
    } catch (MissingResourceException e) {
      ErrorWindow errorWindow = new ErrorWindow(myErrorResources.getString("canvasColorError"));
      this.setColor(DEFAULT_COLOR);
    }
    this.setMaxHeight(height);
    this.setMaxWidth(width);

    double[] initialPos = {0, 0};
    displayTurtlePosition(initialPos);
    this.getChildren().addAll(new HBox(myCanvas, makeAnimationControl()));
    for (double t : myTurtles.keySet()) {
      this.getChildren().add(myTurtles.get(t).getTurtleView());
    }
  }

  private void createTurtle(int width, int height, double id) {
    myTurtles.put(id,
        new GraphicalTurtle(myCanvas, width, height, myResources.getString("defaultTurtle"), id,
            myErrorResources, turtleSelector, this));
  }


  /**
   * Sets new color for the canvas.
   *
   * @param newColor new colow specified
   */
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

  /**
   * manager method which creates all animations for relevant turtles. and puts them in one combined
   * animation.
   *
   * @param moves the list of moves to be animated
   */
  public void moveTurtle(List<MoveInfo> moves) {
    animationSequence.getChildren().clear();
    double[] finalPos = new double[2];
    for (MoveInfo move : moves) {

      //        get(Move.getTurtleId())
      double[] end = {move.getEnd().getX(), move.getEnd().getY()};
      double[] start = {move.getStart().getX(), move.getStart().getY()};
      if (move.getHeading() != lastHeading) {
        lastHeading = move.getHeading();
        animationSequence.getChildren().add(selectedTurtle.getRotateAnimation(move.getHeading()));
      }
      if (!Arrays.equals(start, end)) {
        animationSequence.getChildren()
            .add(selectedTurtle.getMovementAnimation(start, end, move.isPenDown()));
      }
      if (move.clearTrails()) {
        selectedTurtle.clearLines();
      }

      finalPos = end;
    }
    animationSequence.play();
    displayTurtlePosition(finalPos);
  }

  private void displayTurtlePosition(double[] finalPos) {
    this.getChildren().remove(posText);
    posText = new Text(
        String.format(selectedTurtle.getID() + ": (%s,%s)", finalPos[0], finalPos[1]));

    posText.setX(positionLabelX);
    posText.setY(positionLabelY);
    this.getChildren().add(posText);
  }

  /**
   * Sets the Turtle to a new color.
   *
   * @param color new color
   */
  public void setInkColor(String color) {
    if (checkValidColor(color)) {
      selectedTurtle.setInkColor(color);
    }
  }

  /**
   * Sets the Turtle to a new design.
   *
   * @param filepath new design image
   */
  public void setImage(String filepath) {
    selectedTurtle.changeImage(filepath);
  }

  private boolean checkValidColor(String newColor) {
    try {
      Color color = Color.valueOf(newColor);
      return true;
    } catch (IllegalArgumentException e) {
      ErrorWindow errorWindow = new ErrorWindow(myErrorResources.getString("colorSelectionError"));
      return false;
    }

  }

  private String getResourceString(String key) {
    String result = "";
    try {
      result = myResources.getString(key);
    } catch (MissingResourceException e) {
      ErrorWindow errorWindow = new ErrorWindow(e.getClassName());
    }
    return result;
  }

  private VBox makeAnimationControl() {
    VBox controlPanel = new VBox();
    controlPanel.setLayoutX(positionButtonX);
    controlPanel.setLayoutY(positionButtonY);


    Slider slider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_START);
    animationSequence.rateProperty().bind(slider.valueProperty());
    slider.setMaxWidth(SLIDER_WIDTH);

    controlPanel.getChildren().addAll(ControlUtil.makeButton(getResourceString("clearPrompt"),
            e -> selectedTurtle.clearLines()),
        ControlUtil.makeButton(getResourceString("playPrompt"), e -> animationSequence.play()),
        ControlUtil.makeButton(getResourceString("pausePrompt"), e -> animationSequence.pause()),
        slider);

    return controlPanel;
  }

  private void setSelectedTurtle(GraphicalTurtle gt) {
    selectedTurtle = gt;
  }

  /**
   * used for testing
   *
   * @return current color of canvas
   */
  String getCurrentColor() {
    return this.getStyle();
  }

  /**
   * used for testing
   *
   * @return current color of turtle ink
   */
  Paint getTurtleInkColor() {

    return myTurtles.get(0).getInkColor();
  }

  /**
   * used for testing
   *
   * @return current design of turtle
   */
  String getTurtleDesign() {
    return myTurtles.get(0).getLastUsedFile();
  }

  /**
   * used for testing
   *
   * @return current position of turtle
   */
  double[] getTurtleCurrentPos() {
    return myTurtles.get(0).getTurtleCoordinates();
  }

  /**
   * used for testing
   *
   * @return current rotate of turtle
   */
  double getTurtleCurrentRotate() {
    return myTurtles.get(0).getTurtleRotate();
  }

  /**
   * used for testing
   *
   * @return number of lines drawn by a turtle
   */
  int getTurtleDrawnLineCount() {
    return myTurtles.get(0).getLineCount();
  }
}
