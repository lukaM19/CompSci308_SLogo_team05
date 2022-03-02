package slogo.command.actor.move;

import java.util.List;
import slogo.command.actor.ActorCommand;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;

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
