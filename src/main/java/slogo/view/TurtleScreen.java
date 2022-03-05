package slogo.view;


import static slogo.model.Turtle.PEN_COLOR_KEY;
import static slogo.model.Turtle.PEN_STATE_KEY;
import static slogo.model.Turtle.PEN_SIZE_KEY;
import static slogo.model.Turtle.SHAPE_KEY;
import static slogo.model.World.BACKGROUND_KEY;
import static slogo.model.World.PALETTE_KEY;

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
  private Map<Double, GraphicalTurtle> myTurtles = new HashMap<>();
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
  private String[] canvasColorOptions;
  private String[] penColorOptions;
  private String[] turtleColorOptions;
  private double CANVAS_WIDTH;
  private double CANVAS_HEIGHT;

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
    CANVAS_WIDTH=width;
    CANVAS_HEIGHT=height;
    turtleSelector = this::setSelectedTurtle;
    createTurtle(0);
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
  }

  private void createTurtle(double id) {
    GraphicalTurtle newTurtle = new GraphicalTurtle(myCanvas, CANVAS_WIDTH, CANVAS_HEIGHT, myResources.getString("defaultTurtle"), id,
        myErrorResources, turtleSelector, this);
    myTurtles.put(id, newTurtle);
    this.getChildren().add(newTurtle.getTurtleView());
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

      System.out.println(move.getActorID());
      System.out.println(move);
      System.out.println();

      if(!myTurtles.containsKey(move.getActorID())){
        createTurtle(move.getActorID());
      }
      double[] end = {move.getEnd().getX(), move.getEnd().getY()};
      double[] start = {move.getStart().getX(), move.getStart().getY()};
      if (move.getHeading() != lastHeading) {
        lastHeading = move.getHeading();
        animationSequence.getChildren().add(myTurtles.get(move.getActorID()).getRotateAnimation(move.getHeading()));
      }
      if (!Arrays.equals(start, end)) {
        animationSequence.getChildren()
            .add(myTurtles.get(move.getActorID()).getMovementAnimation(start, end, move.isPenDown()));
      }
      if (move.clearTrails()) {
        myTurtles.get(move.getActorID()).clearLines();
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

  private void setInkColorByIndex(int index) {
    setInkColor(penColorOptions[index]);
  }

  private void setColorByIndex(int index) {
    setColor(canvasColorOptions[index]);
  }

  private void setImageByIndex(int index) {
    setImage(turtleColorOptions[index]);
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

  /**
   * makes and returns a map of string and listeners for setting styles of all available
   * characteristics.
   *
   * @return return a map of string functionalities and listeners for this functions
   */
  public Map getStyleListeners() {
    Map<String, Consumer<Object>> consumerMap = new HashMap<>();
    Consumer<Object> canvasConsumer = (i) -> setColorByIndex((Integer) i);
    Consumer<Object> penConsumer = (i) -> setInkColorByIndex((Integer) i);
    Consumer<Object> turtleConsumer = (i) -> setImageByIndex((Integer) i);
    //TODO: implement paletteConsumer, penSizeConsumer;
    Consumer<Object> paletteConsumer = null;
    Consumer<Object> penSizeConsumer = null;
    Consumer<Object> penStateConsumer = null;
    consumerMap.put(BACKGROUND_KEY, canvasConsumer);
    consumerMap.put(PEN_COLOR_KEY, penConsumer);
    consumerMap.put(PALETTE_KEY, paletteConsumer);
    consumerMap.put(SHAPE_KEY, turtleConsumer);
    consumerMap.put(PEN_SIZE_KEY, penSizeConsumer);
    consumerMap.put(PEN_STATE_KEY, penStateConsumer); // up or down, you can decide if you want to implement this or not

    return consumerMap;
  }

  private VBox makeAnimationControl() {
    VBox controlPanel = new VBox();
    controlPanel.setLayoutX(positionButtonX);
    controlPanel.setLayoutY(positionButtonY);

    Slider slider = makeSlider();

    controlPanel.getChildren().addAll(ControlUtil.makeButton(getResourceString("clearPrompt"),
            e -> selectedTurtle.clearLines()),
        ControlUtil.makeButton(getResourceString("playPrompt"), e -> animationSequence.play()),
        ControlUtil.makeButton(getResourceString("pausePrompt"), e -> animationSequence.pause()),
        slider);

    return controlPanel;
  }

  private Slider makeSlider() {
    Slider slider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_START);
    animationSequence.rateProperty().bind(slider.valueProperty());
    slider.setMaxWidth(SLIDER_WIDTH);
    return slider;
  }

  private void setSelectedTurtle(GraphicalTurtle gt) {
    selectedTurtle = gt;
  }

  /**
   * sets the available color options which were defined in ToolBarElements.properties
   *
   * @param canvasOptions all the options of canvas color values defined in toolBarProperties
   * @param penOptions    all the options of pen color values defined in toolBarProperties
   * @param turtleOptions all the options of turtle design values defined in toolBarProperties
   */
  public void setAllStyleOptions(String[] canvasOptions, String[] penOptions,
      String[] turtleOptions) {
    canvasColorOptions = canvasOptions;
    penColorOptions = penOptions;
    turtleColorOptions = turtleOptions;
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

    return selectedTurtle.getInkColor();
  }

  /**
   * used for testing
   *
   * @return current design of turtle
   */
  String getTurtleDesign() {
    return selectedTurtle.getLastUsedFile();
  }

  /**
   * used for testing
   *
   * @return current position of turtle
   */
  double[] getTurtleCurrentPos() {
    return selectedTurtle.getTurtleCoordinates();
  }

  /**
   * used for testing
   *
   * @return current rotate of turtle
   */
  double getTurtleCurrentRotate() {
    return selectedTurtle.getTurtleRotate();
  }

  /**
   * used for testing
   *
   * @return number of lines drawn by a turtle
   */
  int getTurtleDrawnLineCount() {
    return selectedTurtle.getLineCount();
  }
}
