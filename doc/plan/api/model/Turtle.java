package slogo.model;

/**
 * Represents an actor with all the special features of a Turtle, namely the ability to draw with a pen
 */
public interface Turtle extends Actor {
    /**
     * Sets whether the pen is down or not
     * @param down Whether the pen should be down
     */
    void setPenDown(boolean down);

    /**
     *
     * @return If the pen is down
     */
    boolean isPenDown();
}
