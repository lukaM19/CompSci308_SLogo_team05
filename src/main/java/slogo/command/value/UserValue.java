package slogo.command.value;

import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.general.Command;

public class UserValue extends Command {

  private String key;
  private Map<String, Double> userVars;

  /***
   * Command object used by interpreter to execute various actions
   */
  public UserValue(String key) {
    super(null);
    this.key = key;
  }

  public String getVarName() {
    return key;
  }

  /***
   * Defines value if it's null
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    if(userVars == null)
      throw new UserVarMapNotFoundException(getCommandName());
    this.userVars = userVars;
  }

  /***
   * Returns value
   *
   * @return value passed in constructor
   */
  @Override
  protected Double run() throws UserVarMapNotFoundException {
    if (userVars.containsKey(key)) {
      return userVars.get(key);
    }
    return DEFAULT_VALUE;
  }
}
