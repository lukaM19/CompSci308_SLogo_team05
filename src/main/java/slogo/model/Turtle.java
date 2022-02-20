package slogo.model;

/**
 * Represents an actor with all the special features of a Turtle, namely the ability to draw with a pen
 */
public class Turtle extends Actor {
    private boolean penDown;

    public Turtle(String ID) {
        super(ID);
    }

    /**
     * Sets whether the pen is down or not
     * @param down Whether the pen should be down
     */
    public void setPenDown(boolean down) {
        penDown = down;
    }

    /**
     *
     * @return If the pen is down
     */
    public boolean isPenDown() {
        return penDown;
    }
}
