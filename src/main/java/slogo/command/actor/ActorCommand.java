package slogo.command.actor;

import java.util.List;
import java.util.NoSuchElementException;
import slogo.command.exception.actorexception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.World;

public abstract class ActorCommand extends Command {
  public static final String SCALE_KEY = "scale";

  protected List<Actor> actors;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public ActorCommand(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Creates actor instance variable
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    actors = getWorld().getActiveActors();
  }
}
