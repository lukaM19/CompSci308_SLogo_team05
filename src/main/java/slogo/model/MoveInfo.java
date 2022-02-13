package slogo.model;

import javafx.geometry.Point2D;

import java.util.Optional;

public interface MoveInfo {
    void setStart(Point2D start);
    Point2D getStart();

    void setEnd(Point2D end);
    Point2D getEnd();

    void setHeading(double angle);
    double getHeading();

    void setPenDown(boolean down);
    boolean isPenDown();

    void setTurtleID(String id);
    String getTurtleID();

    void setMessage(String msg);
    Optional<String> getMessage();
}
