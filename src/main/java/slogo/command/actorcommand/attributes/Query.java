package slogo.command.actorcommand.attributes;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class Query extends ActorCommand {

//  public static final int QUERY_PARAMETER_NUMBER = 1;
//  public static final int QUERY_INDEX = 0;

  protected String queryVar;

  /***
   * Creates a Command that gets a given attribute from the actor
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public Query(List<Command> parameters)
      throws WrongParameterNumberException {
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
    String queryVar = getImpliedParameter(VAR_NAME_KEY);
    if(!actor.hasVal(queryVar)) {
      throw new UnknownActorValueException(getCommandName() + queryVar);
    }
  }

  /***
   * Returns the desired actor parameter
   *
   * @return the desired actor parameter
   */
  @Override
  public CommandResult run() {
    return actor.getVal(queryVar);
  }
}
