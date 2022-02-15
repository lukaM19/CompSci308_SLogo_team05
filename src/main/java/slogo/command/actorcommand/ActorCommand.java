package slogo.command.actorcommand;

import java.util.List;
import java.util.Map;
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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public ActorCommand(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters, userVars);
    checkForMinParameterLength(ACTOR_COMMAND_MIN_PARAMS);

    this.actor = getActor(this.parameters.get(ACTOR_INDEX));
    this.parameters.remove(ACTOR_INDEX);
  }

  /***
   * Gets the referenced actor and sets it to a private instance variable
   *
   * @param actorIndexWrapper wraps the index in the actor array that the actor is at in a Command
   * @return actor referenced
   * @throws WrongParameterTypeException if the parameter has incorrect type
   */
  private Actor getActor(Command actorIndexWrapper) throws WrongParameterTypeException {
    int actorIndex;
    try {
      actorIndex = (Integer) actorIndexWrapper.execute();
      return world.getActor(actorIndex);
    } catch(Exception e) {
      throw new WrongParameterTypeException(getCommandName() + actorIndexWrapper);
    }
  }
}
