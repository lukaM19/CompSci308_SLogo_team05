package slogo.command.actorcommand.move;

import java.util.List;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Move extends ActorCommand {

  /***
   * Creates a generic Move object that moves an actor
   *
   * @param parameters - parameters for command
   */
  public Move(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Calculates the movement for the actor
   */
  protected abstract void calculateMovement() throws WrongImpliedParameterTypeException;

}
