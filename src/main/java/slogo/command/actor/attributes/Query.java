package slogo.command.actor.attributes;

import static slogo.command.actor.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.model.Actor.HEADING_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Actor.X_COR_KEY;
import static slogo.model.Actor.Y_COR_KEY;
import static slogo.model.Turtle.PEN_KEY;

import java.util.List;
import java.util.Map;
import slogo.command.actor.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.model.World;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"XCoordinate", "YCoordinate", "Heading", "IsPenDown", "IsShowing"})
@ImpliedArgument(keywords = {"XCoordinate", "YCoordinate", "Heading", "IsPenDown", "IsShowing"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"XCoordinate"}, arg = VAR_NAME_KEY, value = X_COR_KEY)
@ImpliedArgument(keywords = {"YCoordinate"}, arg = VAR_NAME_KEY, value = Y_COR_KEY)
@ImpliedArgument(keywords = {"Heading"}, arg = VAR_NAME_KEY, value = HEADING_KEY)
@ImpliedArgument(keywords = {"IsPenDown"}, arg = VAR_NAME_KEY, value = PEN_KEY)
@ImpliedArgument(keywords = {"IsShowing"}, arg = VAR_NAME_KEY, value = VISIBILITY_KEY)

public class Query extends ActorCommand {

//  public static final int QUERY_PARAMETER_NUMBER = 1;
//  public static final int QUERY_INDEX = 0;

  protected String queryVar;

  /***
   * Creates a Command that gets a given attribute from the actor
   *
   * @param parameters - parameters for command
   */
  public Query(List<Command> parameters) {
    super(parameters);
//    checkForExactParameterLength(QUERY_PARAMETER_NUMBER);
  }

  /***
   * Sets up the variable to get
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    queryVar = getImpliedParameter(VAR_NAME_KEY);
    if(!actor.hasVal(queryVar)) {
      throw new UnknownActorValueException(getCommandName() + queryVar);
    }
  }

  /***
   * Returns the desired actor parameter, .get() will always be successful because it was checked
   * previously.
   *
   * @return the desired actor parameter
   */
  @Override
  public Double run() {
    return actor.getVal(queryVar);
  }
}
