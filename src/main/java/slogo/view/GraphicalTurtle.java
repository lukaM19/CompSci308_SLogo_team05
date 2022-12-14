package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * Class which manages the graphical turtle. It creates the turtle and handles its animations.
 * Depends on TurtleScreen.java and JavaFx.
 *
 * @author Luka Mdivani
 */
public class GraphicalTurtle {

  private final GraphicsContext myGraphicsContext;
  private double turtleID;
  private String lastUsedFile;
  private Image myImage;
  private final double SCREEN_WIDTH;
  private final double SCREEN_HEIGHT;
  private final ImageView myImageView = new ImageView();
  private final double[] TURTLE_INITIAL_POSITION = {0, 0};
  private double[] turtleCurrentPos = TURTLE_INITIAL_POSITION;
  private static final String DEFAULT_RESOURCE_PATH = "/slogo/view/";
  private static final String DEFAULT_FILENAME = "defaultTurtle.png";
  private static final int DEFAULT_STROKE = 2;
  private static final Color DEFAULT_INK_COLOR = Color.BLUE;
  private final ResourceBundle myErrorBundle;
  private Consumer<GraphicalTurtle> turtleSelector;
  private AnimationUtil animationMaker;
  private Pane myPane;
  private List<Line> myTrail=new ArrayList<>();
  private int drawnLinesCount = 0;
  private double currentHeading=0;

  /**
   * Main constructor of the graphical turtle object which consists of a graphical context,image,
   * and an associated id.
   *
   * @param turtleScreen the canvas on which the turtle moves
   * @param width        width of the canvas
   * @param height       height of the canvas
   * @param fileName     the filename for the design of the turtle
   * @param id           id of the turtle to be set
   */
  public GraphicalTurtle(Canvas turtleScreen, double width, double height, String fileName, double id,
      ResourceBundle errorBundle, Consumer<GraphicalTurtle> turtleConsumer, Pane rootPane) {
    myPane = rootPane;
    myErrorBundle = errorBundle;
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
    setImage(fileName);
    myImageView.setId("turtle" + id);
    turtleID = id;
    myGraphicsContext = turtleScreen.getGraphicsContext2D();
    myGraphicsContext.setLineWidth(DEFAULT_STROKE);
    myGraphicsContext.setStroke(DEFAULT_INK_COLOR);
    turtleSelector = turtleConsumer;
    animationMaker = new AnimationUtil(width, height);

  }

  private void setImage(String fileName) {

    changeImage(fileName);
    myImageView.setX(translateCanvasX(turtleCurrentPos[0]) - myImage.getWidth() / 2.0);
    myImageView.setY(translateCanvasY(turtleCurrentPos[1]) - myImage.getHeight() / 2.0);

  }

  /**
   * Sets the turtle to a new design
   *
   * @param fileName Filepath to the new image
   */
  public void changeImage(String fileName) {
    try {
      myImage = new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH + fileName));
      myImageView.setImage(myImage);
      myImageView.setOnMouseClicked(e -> {
        turtleSelector.accept(this);
      });
      lastUsedFile = fileName;
    } catch (NullPointerException e) {
      if (!fileName.equals(DEFAULT_FILENAME)) {
        setImage(DEFAULT_FILENAME);
      }
      ErrorWindow err = new ErrorWindow(myErrorBundle.getString("turtleDesignError"));
    }
  }


  private double translateCanvasX(double x) {
    return SCREEN_WIDTH / 2.0 + x;
  }

  private double translateCanvasY(double y) {
    return SCREEN_HEIGHT / 2.0 - y;
  }

  /**
   * returns the visual object of the turtle to be added into the root of the scene
   *
   * @return the ImageView object of the turtle
   */
  public ImageView getTurtleView() {
    return myImageView;
  }

  public double[] getTurtleCoordinates() {
    return turtleCurrentPos;
  }


  /**
   * Makes an animaton of turtle rotating to the specified degree marker.
   *
   * @param degree degrees to rotate to
   * @return return the rotation animation
   */
  public Animation getRotateAnimation(double degree) {
      currentHeading=degree;
    return animationMaker.makeRotateAnimation(degree, myImageView);
  }

  /**
   * gets a movement animation for the specified turtle
   *
   * @param start   start coordinate of turtle movement(in user coordinates)
   * @param end     end coordinate of turtle movement(in user coordinates)
   * @param penDown should a line be drawn behind the turtle
   * @return returns the new movement animation
   */
  public Animation getMovementAnimation(double[] start, double[] end, boolean penDown) {
    double[] translatedStart = {translateCanvasX(start[0]), translateCanvasY(start[1])};
    double[] translatedEnd = {translateCanvasX(end[0]), translateCanvasY(end[1])};
    turtleCurrentPos = end;
    Animation result = animationMaker.makeMoveAnimation(myGraphicsContext, translatedStart,
        translatedEnd, penDown, myImageView);
    result.setOnFinished(e -> {
      if(penDown) {
        replaceWithRemovableLine(translatedStart, translatedEnd);
        drawnLinesCount=drawnLinesCount+1;
      }
    });
    return result;
  }

  private void replaceWithRemovableLine(double[] translatedStart, double[] translatedEnd) {
    Line test = new Line(translatedStart[0], translatedStart[1], translatedEnd[0],
        translatedEnd[1]);
    test.setStroke(myGraphicsContext.getStroke());
    test.setStrokeWidth(myGraphicsContext.getLineWidth());
    myPane.getChildren().add(test);
    test.toBack();
    myTrail.add(test);
    clearGraphicContext();
  }


  /**
   * Sets a new user selected color in which lines will be drawn.
   *
   * @param color the new color
   */
  public void setInkColor(String color) {
    myGraphicsContext.setStroke(Color.valueOf(color));
  }

  /**
   * clears the lines drawn by the turtle, when user requests to do so.
   */
  public void clearLines() {
    for (Line line : myTrail) {
      myPane.getChildren().remove(line);
    }
  }

  private void clearGraphicContext() {
    myGraphicsContext.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
  }

  public double getID() {
    return turtleID;
  }

  /**
   * used for testing
   *
   * @return color of the turtle ink
   */
  Paint getInkColor() {
    return myGraphicsContext.getStroke();
  }

  /**
   * used for testing
   *
   * @return last file used for design
   */
  String getLastUsedFile() {
    return lastUsedFile;
  }

  /**
   * used for testing
   *
   * @return current line count
   */
  int getLineCount() {
    return drawnLinesCount;
  }

  /**
   * used for testing
   *
   * @return current rotate
   */
  double getTurtleRotate() {
    return currentHeading;
  }
}
