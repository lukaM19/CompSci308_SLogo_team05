package slogo.model;

import slogo.command.logic.Logic;

/**
 * Represents an actor with all the special features of a Turtle, namely the ability to draw with a pen
 */
public class Turtle extends Actor {
    public static final String PEN_KEY = "pen";

    public Turtle(String ID) {
        super(ID);
        actorVars.put(PEN_KEY, 1d);
    }

    /**
     * Sets whether the pen is down or not
     * @param down Whether the pen should be down
     */
    public void setPenDown(boolean down) {
        actorVars.put(PEN_KEY, Logic.returnValues.get(down));
    }

    /**
     *
     * @return If the pen is down
     */
    public boolean isPenDown() {
        return Logic.acceptedValues.get(actorVars.get(PEN_KEY));
    }
}
