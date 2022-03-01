package slogo.view;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * A helper class which has the sole purpose of creating animations for specific turtles. Depends
 * on JavaFx and <Class>GraphicalTurtle.java</Class>
 *
 * @author Luka Mdivani
 */
public class AnimationUtil {

  private final double SCREEN_WIDTH;
  private final double MOVEMENT_SPEED = 0.5;
  private final double ROTATE_SPEED = 0.3;
  private final double SCREEN_HEIGHT;

  public AnimationUtil(double width, double height) {
    SCREEN_HEIGHT = height;
    SCREEN_WIDTH = width;
  }

  /**
   * Makes an animation of turtle rotating to the specified degree marker.
   *
   * @param degree degrees to rotate to
   * @return return the rotation animation
   */
  public Animation makeRotateAnimation(double degree, ImageView myImageView) {

    RotateTransition rt = new RotateTransition(Duration.seconds(ROTATE_SPEED), myImageView);
    rt.setToAngle(degree);

    return rt;
  }

  /**
   * Creates an animation of a turtle between two points. Draws line dynamically behind the turtle
   * if specified.
   *
   * @param start       start coordinate of turtle movement(in canvas friendly coordinates)
   * @param end         end coordinate of turtle movement(in canvas friendly coordinates)
   * @param penDown     should a line be drawn behind the turtle
   * @param myImageView the turtle on which animation should be made.
   * @return returns the new movement animation
   */
  public Animation makeMoveAnimation(GraphicsContext myGraphicsContext, double[] start,
      double[] end, boolean penDown, ImageView myImageView) {
    Path path = new Path();
    path.getElements().addAll(new MoveTo(start[0],
            start[1]),
        new LineTo(end[0], end[1]));
    PathTransition pt = new PathTransition(Duration.seconds(MOVEMENT_SPEED), path, myImageView);
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

}
