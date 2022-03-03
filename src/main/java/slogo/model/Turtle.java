package slogo.model;

import static slogo.command.general.Command.DEFAULT_VALUE;

import slogo.command.logic.Logic;

/**
 * Represents an actor with all the special features of a Turtle, namely the ability to draw with a pen
 */
public class Turtle extends Actor {
    public static final String PEN_KEY = "pen";
    public static final String PEN_COLOR_KEY = "penColor";
    public static final String PEN_SIZE_KEY = "penSize";
    public static final String SHAPE_KEY = "shape";

    public static final double PEN_DOWN = 1d;
    public static final double PEN_UP = 0d;
    public static final double PEN_SIZE_DEFAULT = 5d;

    public Turtle(double ID) {
        super(ID);
        actorVars.put(PEN_KEY, PEN_DOWN);
        actorVars.put(PEN_COLOR_KEY, DEFAULT_VALUE);
        actorVars.put(SHAPE_KEY, DEFAULT_VALUE);
        actorVars.put(PEN_SIZE_KEY, PEN_SIZE_DEFAULT);
    }
}
