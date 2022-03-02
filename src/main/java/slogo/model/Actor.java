package slogo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.geometry.Point2D;

/**
 * Represents an actor that can move around the world and has a heading
 */
public class Actor {
    public static final String X_COR_KEY = "xcor";
    public static final String Y_COR_KEY = "ycor";
    public static final String HEADING_KEY = "heading";
    public static final String VISIBILITY_KEY = "visible";

    private double ID;
    protected Map<String, Double> actorVars;

    public Actor(double ID) {
        this.ID = ID;

        actorVars = new HashMap<>();
        actorVars.put(X_COR_KEY, 0d);
        actorVars.put(Y_COR_KEY, 0d);
        actorVars.put(HEADING_KEY, 0d);
        actorVars.put(VISIBILITY_KEY, 1d);
    }

    /**
     *
     * @return The ID of this actor
     */
    public double getID() {
        return ID;
    }

    /**
     *
     * @return The current position of this actor
     */
    public Point2D getPosition() {
        return new Point2D(actorVars.get(X_COR_KEY), actorVars.get(Y_COR_KEY));
    }

    /**
     * Sets the current position of this actor
     * @param pos The position to set this to
     */
    public void setPosition(Point2D pos) {
        actorVars.put(X_COR_KEY, pos.getX());
        actorVars.put(Y_COR_KEY, pos.getY());
    }

    /**
     *
     * @return The current heading of this actor
     */
    public double getHeading() {
        return actorVars.get(HEADING_KEY);
    }

    /**
     * Sets the current heading of this actor
     * @param angle The heading to set this to
     */
    public void setHeading(double angle) {
        actorVars.put(HEADING_KEY, angle);
    }

    /**
     * Moves this actor a certain amount in a specific direction.
     * The actors heading does not necessarily change to point in the direction of motion.
     * @param angle The angle to move at relative to the actors current heading (in radians)
     * @param distance The distance to move
     */
    public void move(double distance, double angle) {
        double moveAngle = getHeading() + angle;
        actorVars.put(X_COR_KEY, getPosition().getX() + distance * Math.sin(moveAngle));
        actorVars.put(Y_COR_KEY, getPosition().getY() + distance * Math.cos(moveAngle));
    }

    /**
     * Turns this actor by the specified amount. Essentially just adds the angle specified to the current heading.
     * @param angle How much to turn the actor (in radians)
     */
    public void turn(double angle) {
        setHeading(getHeading() + angle);
    }

    /***
     * Returns if requested actor variable is present
     *
     * @param key to look for in actorVars
     * @return if the map has that key
     */
    public boolean hasVal(String key) {
        return actorVars.containsKey(key);
    }

    /***
     * Returns the requested value that is paired with the key
     *
     * @param key to look for in actorVars
     * @return value paired to the given key
     */
    public Double getVal(String key) {
        return actorVars.get(key);
    }

    /***
     * Puts the given key-value pair in the actorVars map
     *
     * @param key is the key to put in the map
     * @param val is the value to put in the map
     */
    public void putVal(String key, double val) {
        actorVars.put(key, val);
    }
}
