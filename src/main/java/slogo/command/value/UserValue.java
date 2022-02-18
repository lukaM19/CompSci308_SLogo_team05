package slogo.command.value;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.model.World;

public class UserValue extends GenericValue {

  private Map<String, Object> userVars;

  /***
   * Command object used by interpreter to execute various actions
   * @param parameters - the parameters for command
   */
  public UserValue(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Defines value if it's null
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    this.userVars = userVars;
    if(value == null) {
      value = parameters.get(GENERIC_VALUE_INDEX).execute(world, userVars);
    }
  }

  /***
   * Returns value
   *
   * @return value passed in constructor
   */
  @Override
  protected Object run() {
    String key = value.toString();
    if(userVars.containsKey(key)) {
      return userVars.get(key);
    }
    return DEFAULT_VALUE;
  }
}
