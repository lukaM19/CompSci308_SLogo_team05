package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.ActorCommand.ActorCommand;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class Move extends ActorCommand {

  /***
   * Creates a generic Move object that moves an actor
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Move(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  /***
   * Calculates the movement for the actor
   *
   * @throws WrongParameterTypeException if too many/few parameters
   * @throws WrongParameterNumberException if parameters have incorrect type
   */
  protected abstract void calculateMovement()
      throws WrongParameterTypeException, WrongParameterNumberException;
}
