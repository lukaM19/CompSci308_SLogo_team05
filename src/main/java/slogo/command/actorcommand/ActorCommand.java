package slogo.command.actorcommand;

import java.util.List;
import java.util.Map;
import slogo.command.exception.actorexception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.World;

public abstract class ActorCommand extends Command {
  public static final int ACTOR_INDEX = 0;

  protected Actor actor;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public ActorCommand(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Gets the referenced actor and sets it to a private instance variable
   *
   * @param world to get actor from
   * @return actor index referenced
   * @throws ActorNotFoundException if the actor can't be found in the world
   */
  private Actor getActor(World world) throws ActorNotFoundException, ImpliedParameterException {
    String actorName = getImpliedParameter(VAR_NAME_KEY);
    try {
      int actorIndex = Integer.parseInt(actorName);
      if(actorIndex >= world.getActorCount())
        throw new ActorNotFoundException(getCommandName() + actorIndex);
      return world.getActor(actorIndex);
    } catch(NumberFormatException e) {
      Actor potentialActor = world.getActorByID(actorName);
      if(potentialActor != null)
        return world.getActorByID(actorName);
      throw new ActorNotFoundException(getCommandName() + actorName);
    } catch (NullPointerException e) {
      throw new ImpliedParameterNotFoundException(getCommandName() + "world");
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
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    actor = getActor(world);
  }
}
