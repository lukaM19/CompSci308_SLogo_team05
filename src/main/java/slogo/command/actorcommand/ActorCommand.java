package slogo.command.actorcommand;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.Actor;
import slogo.model.World;

public abstract class ActorCommand extends Command {
  public static final int ACTOR_COMMAND_MIN_PARAMS = 1;
  public static final int ACTOR_INDEX = 0;

  protected Actor actor;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public ActorCommand(List<Command> parameters)
      throws WrongParameterNumberException {

    super(parameters);
    checkForMinParameterLength(ACTOR_COMMAND_MIN_PARAMS);
  }

  /***
   * Gets the referenced actor index and sets it to a private instance variable
   *
   * @param actorIndexWrapper wraps the index in the actor array that the actor is at in a Command
   * @param world to get actor index from
   * @param userVars to pass execute
   * @return actor index referenced
   * @throws WrongParameterTypeException if the parameter has incorrect type
   */
  private int getActorIndex(Command actorIndexWrapper, World world, Map<String, Object> userVars) throws WrongParameterTypeException {
    try {
      return (Integer) actorIndexWrapper.execute(world, userVars);
    } catch(Exception e) {
      throw new WrongParameterTypeException(getCommandName() + actorIndexWrapper);
    }
  }

  /***
   * Gets the referenced actor and sets it to a private instance variable
   *
   * @param world to get actor from
   * @return actor index referenced
   * @throws ActorNotFoundException if the actor can't be found in the world
   */
  private Actor getActor(World world, int actorNumber) throws ActorNotFoundException {
    try {
      return world.getActor(actorNumber);
    } catch(Exception e) {
      throw new ActorNotFoundException(getCommandName() + actorNumber);
    }
  }

  /***
   * Creates actor instance variable
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    int actorNumber = getActorIndex(this.parameters.get(ACTOR_INDEX), world, userVars);
    this.parameters.remove(ACTOR_INDEX);
    actor = getActor(world, actorNumber);
  }
}
