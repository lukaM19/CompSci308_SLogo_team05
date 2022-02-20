package slogo.model;

import java.util.Optional;
import javafx.geometry.Point2D;

public class MoveInfoTest implements MoveInfo{

  public MoveInfoTest(double x, double y) {

  }

  public MoveInfoTest(double angle) {
  }

  @Override
  public void setStart(Point2D start) {

  }

  @Override
  public Point2D getStart() {
    return null;
  }

  @Override
  public void setEnd(Point2D end) {

  }

  @Override
  public Point2D getEnd() {
    return null;
  }

  @Override
  public void setHeading(double angle) {

  }

  @Override
  public double getHeading() {
    return 0;
  }

  @Override
  public void setPenDown(boolean down) {

  }

  @Override
  public boolean isPenDown() {
    return false;
  }

  @Override
  public void setActorID(String id) {

  }

  @Override
  public String getActorID() {
    return null;
  }

  @Override
  public void setMessage(String msg) {

  }

  @Override
  public Optional<String> getMessage() {
    return Optional.empty();
  }
}
