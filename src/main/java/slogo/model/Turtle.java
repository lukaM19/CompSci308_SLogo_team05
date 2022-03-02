package slogo.model;

import slogo.command.logic.Logic;

/**
 * Represents an actor with all the special features of a Turtle, namely the ability to draw with a pen
 */
public class Turtle extends Actor {
    public static final String PEN_KEY = "pen";
    public static final double PEN_DOWN = 1d;
    public static final double PEN_UP = 0d;

    public Turtle(double ID) {
        super(ID);
        actorVars.put(PEN_KEY, PEN_DOWN);
    }

    /**
     * Sets whether the pen is down or not
     * @param down Whether the pen should be down
     */
    public void setPenDown(boolean down) {
        actorVars.put(PEN_KEY, Logic.RETURN_VALUES.get(down));
    }

    /**
     *
     * @return If the pen is down
     */
    public boolean isPenDown() {
        return Logic.ACCEPTED_VALUES.get(actorVars.get(PEN_KEY));
    }
}
