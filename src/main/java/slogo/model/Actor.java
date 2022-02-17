package slogo.model;

import javafx.geometry.Point2D;

/**
 * Represents an actor that can move around the world and has a heading
 */
public interface Actor {
    /**
     *
     * @return The ID of this actor
     */
    String getID();

    /**
     * Sets the ID of this actor
     * @param pos The ID to set it to
     */
    void setID(String id);

    /**
     *
     * @return The current position of this actor
     */
    Point2D getPosition();

    /**
     * Sets the current position of this actor
     * @param pos The position to set this to
     */
    void setPosition(Point2D pos);

    /**
     *
     * @return The current heading of this actor
     */
    double getHeading();

    /**
     * Sets the current heading of this actor
     * @param angle The heading to set this to
     */
    void setHeading(double angle);

    /**
     * Moves this actor a certain amount in a specific direction.
     * The actors heading does not necessarily change to point in the direction of motion.
     * @param angle The angle to move at relative to the actors current heading
     * @param distance The distance to move
     */
    void move(double distance, double angle);

    /**
     * Turns this actor by the specified amount. Essentially just adds the angle specified to the current heading.
     * @param angle How much to turn the actor
     */
    void turn(double angle);

    //TODO: DELETE METHODS UNDER THIS WHEN MERGING, THIS IS ONLY FOR TESTING

    void putVal(String key, Object val);

    boolean hasVal(String key);

    Object getVal(String key);
}
