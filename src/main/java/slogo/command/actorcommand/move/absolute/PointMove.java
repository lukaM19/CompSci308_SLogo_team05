package slogo.command.actorcommand.move.absolute;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.Environment;
import slogo.model.World;

public abstract class PointMove extends Move {
  public static final int ABSOLUTE_MOVE_PARAM_NUMBER = 2;
  public static final int X_INDEX = 0;
  public static final int Y_INDEX = 1;

  protected double[] coords;
  protected World world;

  /***
   * Creates a Command Object that acts on an actor given coordinates
   *
   * @param parameters - parameters for command
   */
  public PointMove(List<Command> parameters) {
    super(parameters);
  }

  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    checkForExactParameterLength(ABSOLUTE_MOVE_PARAM_NUMBER);
    coords = new double[ABSOLUTE_MOVE_PARAM_NUMBER];
    for (int i = 0; i < coords.length; i++) {
      coords[i] = executeParameter(i, world, env).returnVal();
    }
    this.world = world;
    this.environment = env;
    calculateMovement();
  }
}
