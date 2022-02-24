package slogo.view;

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
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class GraphicalTurtle {

  private final GraphicsContext myGraphicsContext;
  private int turtleID;
  private String lastUsedFile;
  private Image myImage;
  private int SCREEN_WIDTH;
  private int SCREEN_HEIGHT;
  private ImageView myImageView = new ImageView();
  private double[] TURTLE_INITIAL_POSITION = {0, 0};
  private double[] turtleXCoordinate = TURTLE_INITIAL_POSITION;
  private final String DEFAULT_RESOURCE_PATH = "/slogo/";
  private final String DEFAULT_FILENAME = "defaultTurtle.png";
  private final int DEFAULT_STROKE = 2;
  private final Paint DEFAULT_INK_COLOR = Color.BLUE;
  private Rotate rotation = new Rotate();
  private int drawnLinesCount = 0;
  private double previousX = 0;
  private double previousY = 0;
  private int signX=-1;
  private int signY=-1;

  public GraphicalTurtle(Canvas turtleScreen, int width, int height, String fileName, int id) {
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
    myImageView.setX(translateCanvasX(turtleXCoordinate[0])-myImage.getWidth()/2.0);
    myImageView.setY(translateCanvasY(turtleXCoordinate[1])-myImage.getHeight()/2.0);
    previousX=myImage.getWidth()/2.0;
    previousY=myImage.getHeight()/2.0;
    myImageView.getTransforms().add(rotation);

  }

  public void changeImage(String fileName) {
    try {
      myImage = new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH + fileName));
      myImageView.setImage(myImage);
      lastUsedFile = fileName;
    } catch (NullPointerException e) {
      setImage(DEFAULT_FILENAME);
      ErrorWindow err = new ErrorWindow("Invalid Image Filepath");
    }
  }

  public void drawLine(double[] end) {
    double[] start = {turtleXCoordinate[0], turtleXCoordinate[1]};
    myGraphicsContext.strokeLine(translateCanvasX(start[0]),
        translateCanvasY(start[1]), translateCanvasX(end[0]),
        translateCanvasY(end[1]));
    turtleXCoordinate = end;
    drawnLinesCount++;
  }

  private double translateCanvasX(double x) {
    return SCREEN_WIDTH / 2.0 + x;
  }

  private double translateCanvasY(double y) {
    return SCREEN_HEIGHT / 2.0 - y;
  }

  public ImageView getTurtleView() {
    return myImageView;
  }

  public double[] getTurtleCoordinates() {
    return turtleXCoordinate;
  }

  public Animation animateTurtle(double[] start, double[] end, boolean penDown) {
    Animation resultAnimation = makeMovementAnimation(start, end, penDown);
    return resultAnimation;
  }

  public void setRotate(double[] end, double degree) {
    rotation.setPivotX(translateCanvasX(end[0]));
    rotation.setPivotY(translateCanvasY(end[1]));
    rotation.setAngle(rotation.getAngle() + degree);

  }

  public Animation makeRotateAnimation(double degree) {

    RotateTransition rt = new RotateTransition(Duration.seconds(1), myImageView);
    rt.setByAngle(degree);

    return  rt;
  }

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

          double x = translateCanvasX(start[0]) + myImageView.getTranslateX() -  previousX + 7;
          double y = translateCanvasY(start[1]) + myImageView.getTranslateY() -  previousY + 13;

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
    pt.setOnFinished(e -> {
      previousX = myImageView.getTranslateX() + 7;
      previousY = myImageView.getTranslateY() + 13;
    });
    return new SequentialTransition(myImageView, pt);
  }

  private double translateImageOriginX(double x) {
    return x - myImage.getWidth() / 2;
  }

  private double translateImageOriginY(double y) {
    return y - myImage.getHeight();
  }

  public void setInkColor(String color) {
    myGraphicsContext.setStroke(Color.valueOf(color));
  }

  public Paint getInkColor() {
    return myGraphicsContext.getStroke();
  }

  public String getLastUsedFile() {
    return lastUsedFile;
  }

  public int getLineCount() {
    return drawnLinesCount;
  }

  public double getTurtleRotate() {
    return rotation.getAngle();
  }
}
