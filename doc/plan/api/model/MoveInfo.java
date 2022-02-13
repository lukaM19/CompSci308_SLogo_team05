package slogo.model;

import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * Contains data on what actions occurred after a command is executed
 */
public interface MoveInfo {
    /**
     * Sets the starting position of this movement
     * @param start The starting position
     */
    void setStart(Point2D start);

    /**
     * Gets the starting position of this movement
     * @return The starting position
     */
    Point2D getStart();

    /**
     * Sets the ending position of this movement
     * @param end The ending position
     */
    void setEnd(Point2D end);

    /**
     * Gets the ending position of this movement
     * @return The ending position
     */
    Point2D getEnd();

    /**
     * Sets the heading to turn to for this movement
     * @param angle The heading to turn to
     */
    void setHeading(double angle);

    /**
     * Gets the heading to turn to for this movement
     * @return The heading to turn to
     */
    double getHeading();

    /**
     * Sets the state of the pen on this actor
     * @param down Whether the pen is down or not
     */
    void setPenDown(boolean down);

    /**
     * Gets the state of the pen on this actor
     * @return Whether the pen is down or not
     */
    boolean isPenDown();

    /**
     * Sets the ID of the actor that is affected by this movement
     * @param id The ID of the moving actor
     */
    void setActorID(String id);

    /**
     * Gets the ID of the actor that is affected by this movement
     * @return The ID of the moving actor
     */
    String getActorID();

    /**
     * Sets the message to be printed before this movement occurs
     * @param msg The message to be printed
     */
    void setMessage(String msg);

    /**
     * Gets the message to be printed before this movement occurs
     * @return An Optional which will be empty if there is no message and will otherwise contain the message
     */
    Optional<String> getMessage();
}
