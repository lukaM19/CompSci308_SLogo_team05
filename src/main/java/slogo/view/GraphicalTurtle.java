package slogo.view;

import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Class which manages the graphical turtle. It creates the turtle and handles its animations.
 * Depends on TurtleScreen.java and JavaFx.
 *
 * @author Luka Mdivani
 */
public class GraphicalTurtle {

  private final GraphicsContext myGraphicsContext;
  private int turtleID;
  private String lastUsedFile;
  private Image myImage;
  private final int SCREEN_WIDTH;
  private final int SCREEN_HEIGHT;
  private final ImageView myImageView = new ImageView();
  private double[] TURTLE_INITIAL_POSITION = {0, 0};
  private double[] turtleXCoordinate = TURTLE_INITIAL_POSITION;
  private final String DEFAULT_RESOURCE_PATH = "/slogo/view/";
  private final String DEFAULT_FILENAME = "defaultTurtle.png";
  private final int DEFAULT_STROKE = 2;
  private final Paint DEFAULT_INK_COLOR = Color.BLUE;
  private int drawnLinesCount = 0;
  private ResourceBundle myErrorBundle;

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
  public GraphicalTurtle(Canvas turtleScreen, int width, int height, String fileName, int id,
      ResourceBundle errorBundle) {
    myErrorBundle = errorBundle;
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
    setImage(fileName);
    myImageView.setId("turtle" + id);
    turtleID = id;
    myGraphicsContext = turtleScreen.getGraphicsContext2D();
    myGraphicsContext.setLineWidth(DEFAULT_STROKE);
    myGraphicsContext.setStroke(DEFAULT_INK_COLOR);

  }

  private void setImage(String fileName) {

    changeImage(fileName);
    myImageView.setX(translateCanvasX(turtleXCoordinate[0]) - myImage.getWidth() / 2.0);
    myImageView.setY(translateCanvasY(turtleXCoordinate[1]) - myImage.getHeight() / 2.0);

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
    return turtleXCoordinate;
  }


  /**
   * Makes an animaton of turtle rotating to the specified degree marker.
   *
   * @param degree degrees to rotate to
   * @return return the rotation animation
   */
  public Animation makeRotateAnimation(double degree) {

    RotateTransition rt = new RotateTransition(Duration.seconds(1), myImageView);
    rt.setToAngle(degree);

    return rt;
  }

  /**
   * Creates an animation of a turtle between two points. Draws line dynamically behind the turtle
   * if specified.
   *
   * @param start   start coordinate of turtle movement
   * @param end     end coordinate of turtle movement
   * @param penDown should a line be drawn behind the turtle
   * @return returns the new movement animation
   */
  public Animation makeMovementAnimation(double[] start, double[] end, boolean penDown) {
    Path path = new Path();
    path.getElements().addAll(new MoveTo(translateCanvasX(start[0]),
            translateCanvasY(start[1])),
        new LineTo(translateCanvasX(end[0]),
            translateCanvasY(end[1])));

    PathTransition pt = new PathTransition(Duration.seconds(0.5), path, myImageView);
    if (penDown) {
      pt.currentTimeProperty().addListener(new ChangeListener<Duration>() {
        double[] oldLocation = null;

        @Override
        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
            Duration newValue) {

          double x = SCREEN_WIDTH / 2.0 + myImageView.getTranslateX();
          double y = SCREEN_HEIGHT / 2.0 + myImageView.getTranslateY();

          if (oldLocation == null) {
            oldLocation = new double[2];
            oldLocation[0] = x;
            oldLocation[1] = y;
            return;
          }

          myGraphicsContext.strokeLine(oldLocation[0], oldLocation[1], x, y);
          oldLocation[0] = x;
          oldLocation[1] = y;
        }
      });

    }
    return new SequentialTransition(myImageView, pt);
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
    return 0;//rotation.getAngle();
  }
}
