package slogo.command.actorcommand;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import slogo.command.exception.actorexception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.Environment;
import slogo.model.World;

public abstract class ActorCommand extends Command {
  public static final int ACTOR_INDEX = 0;
  public static final String ACTOR_ID_KEY = "actorID";
  public static final String SCALE_KEY = "scale";

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
    String actorName = getImpliedParameter(ACTOR_ID_KEY);
    try {
      return world.getActorByID(actorName);
    } catch (NoSuchElementException e) {
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
  protected void setUpExecution(World world, Environment env) throws CommandException {
    actor = getActor(world);
  }
}
