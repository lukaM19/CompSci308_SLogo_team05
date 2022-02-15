package slogo.command.actorcommand.move;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class Move extends ActorCommand {

  /***
   * Creates a generic Move object that moves an actor
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Move(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    calculateMovement();
  }

  /***
   * Calculates the movement for the actor
   */
  protected abstract void calculateMovement();
}
