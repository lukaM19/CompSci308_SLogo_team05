package slogo.model;

import javafx.geometry.Point2D;

/**
 * Represents an actor that can move around the world and has a heading
 */
public class Actor {
    private String ID;
    private Point2D position;
    private double heading;

    public Actor(String ID) {
        this.ID = ID;
    }

    /**
     *
     * @return The ID of this actor
     */
    String getID() {
        return ID;
    }

    /**
     *
     * @return The current position of this actor
     */
    Point2D getPosition() {
        return position;
    }

    /**
     * Sets the current position of this actor
     * @param pos The position to set this to
     */
    void setPosition(Point2D pos) {
        position = pos;
    }

    /**
     *
     * @return The current heading of this actor
     */
    double getHeading() {
        return heading;
    }

    /**
     * Sets the current heading of this actor
     * @param angle The heading to set this to
     */
    void setHeading(double angle) {
        heading = angle;
    }

    /**
     * Moves this actor a certain amount in a specific direction.
     * The actors heading does not necessarily change to point in the direction of motion.
     * @param angle The angle to move at relative to the actors current heading (in radians)
     * @param distance The distance to move
     */
    void move(double distance, double angle) {
        double moveAngle = heading + angle;
        position = position.add(distance * Math.sin(moveAngle), distance * Math.cos(moveAngle));
    }

    /**
     * Turns this actor by the specified amount. Essentially just adds the angle specified to the current heading.
     * @param angle How much to turn the actor (in radians)
     */
    void turn(double angle) {
        heading += angle;
    }
}
