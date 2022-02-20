package slogo.command.actorcommand.move;

import java.util.List;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Move extends ActorCommand {

  /***
   * Creates a generic Move object that moves an actor
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Move(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Calculates the movement for the actor
   */
  protected abstract void calculateMovement();

}
