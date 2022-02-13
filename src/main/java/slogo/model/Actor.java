package slogo.model;

import javafx.geometry.Point2D;

public interface Actor {
    Point2D getPosition();
    void setPosition(Point2D pos);
    double getHeading();
    void setHeading(double angle);
    void move(double angle, double distance);
    void turn(double angle);
}
