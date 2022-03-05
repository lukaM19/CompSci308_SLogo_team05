package slogo.model;

import javafx.geometry.Point2D;

import java.util.Objects;
import java.util.Optional;

/**
 * Contains data on what actions occurred after a command is executed
 */
public class MoveInfo {
    private static final double POSITION_TOLERANCE = 0.00001;

    private Point2D startPos;
    private Point2D endPos;
    private double heading;
    private boolean penDown;
    private String actorID;
    private String message;
    private boolean clearTrails;

    public MoveInfo(String actorID, Point2D position, double angle) {
        this.actorID = actorID;
        heading = angle;
        endPos = startPos = position;
    }

    public MoveInfo(String actorID, Point2D start, Point2D end, double angle, boolean penDown) {
        this.actorID = actorID;
        heading = angle;
        startPos = start;
        endPos = end;
        this.penDown = penDown;
    }

    public MoveInfo(String msg) {
        message = msg;
    }

    public MoveInfo(String actorID, Point2D start, Point2D end, double angle, boolean penDown, boolean clearTrails) {
        heading = angle;
        startPos = start;
        endPos = end;
        this.penDown = penDown;
        this.clearTrails = clearTrails;
    }

    /**
     * Sets the starting position of this movement
     * @param start The starting position
     */
    public void setStart(Point2D start) {
        startPos = start;
    }

    /**
     * Gets the starting position of this movement
     * @return The starting position
     */
    public Point2D getStart() {
        return startPos;
    }

    /**
     * Sets the ending position of this movement
     * @param end The ending position
     */
    public void setEnd(Point2D end) {
        endPos = end;
    }

    /**
     * Gets the ending position of this movement
     * @return The ending position
     */
    public Point2D getEnd() {
        return endPos;
    }

    /**
     * Sets the heading to turn to for this movement
     * @param angle The heading to turn to
     */
    public void setHeading(double angle) {
        heading = angle;
    }

    /**
     * Gets the heading to turn to for this movement
     * @return The heading to turn to
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Sets the state of the pen on this actor
     * @param down Whether the pen is down or not
     */
    public void setPenDown(boolean down) {
        penDown = down;
    }

    /**
     * Gets the state of the pen on this actor
     * @return Whether the pen is down or not
     */
    public boolean isPenDown() {
        return penDown;
    }

    /**
     * Sets the ID of the actor that is affected by this movement
     * @param id The ID of the moving actor
     */
    public void setActorID(String id) {
        actorID = id;
    }

    /**
     * Gets the ID of the actor that is affected by this movement
     * @return The ID of the moving actor
     */
    public String getActorID() {
        return actorID;
    }

    /***
     * Gets if trails should be cleared or not
     * @return if trails should be cleared
     */
    public boolean clearTrails() {
        return clearTrails;
    }

    /***
     * Sets clear trails boolean
     * @param clearTrails whether the trails should be cleared
     */
    public void setClearTrails(boolean clearTrails) {
        this.clearTrails = clearTrails;
    }

    /**
     * Sets the message to be printed before this movement occurs
     * @param msg The message to be printed
     */
    public void setMessage(String msg){
        message = msg;
    }

    /**
     * Gets the message to be printed before this movement occurs
     * @return An Optional which will be empty if there is no message and will otherwise contain the message
     */
    public Optional<String> getMessage(){
        if (message == null || message.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(message);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof MoveInfo other)) {
            return false;
        }
        return (other.message == message || other.message.equals(message))
                && (other.actorID == actorID || other.actorID.equals(actorID))
                && other.startPos.distance(startPos) < POSITION_TOLERANCE
                && other.endPos.distance(endPos) < POSITION_TOLERANCE
                && other.heading == heading
                // If the turtle doesn't move, pen status doesn't matter for equality
                && (endPos.equals(startPos) || other.penDown == penDown);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPos, endPos, heading, penDown, actorID, message, clearTrails);
    }

    @Override
    public String toString() {
        return "MoveInfo{" +
                "startPos=" + startPos +
                ", endPos=" + endPos +
                ", heading=" + heading +
                ", penDown=" + penDown +
                ", actorID='" + actorID + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
