package slogo.command.actorcommand;

import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.UnknownActorValueException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public class Query extends ActorCommand{

  public static final int QUERY_PARAMETER_NUMBER = 1;
  public static final int QUERY_INDEX = 0;

  protected String queryVar;

  /***
   * Creates a Command that gets a given attribute from the actor
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   * @throws UnknownActorValueException if the actor's value cannot be found
   */
  public Query(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException, UnknownActorValueException {
    super(world, parameters, userVars);
    checkForExactParameterLength(QUERY_PARAMETER_NUMBER);
    setQueryVar(this.parameters.get(QUERY_INDEX));
  }

  /***
   * Sets up the variable to get
   *
   * @param queryVarWrapper wraps the query String in a Command
   * @throws UnknownActorValueException if the actor's value cannot be found
   */
  private void setQueryVar(Command queryVarWrapper) throws UnknownActorValueException {
    queryVar = queryVarWrapper.execute().toString();
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
  public Object execute() {
    return actor.getVal(queryVar);
  }
}
