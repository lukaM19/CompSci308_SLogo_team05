package slogo.command.value;

import java.util.Map;
import java.util.Optional;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.ParameterNotFoundException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.Environment;
import slogo.model.World;

public class UserValue extends Command {

  private String key;

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
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    if(env == null)
      throw new UserVarMapNotFoundException(getCommandName());
    this.environment = env;
  }

  /***
   * Returns value
   *
   * @return value passed in constructor
   */
  @Override
  protected Double run() throws UserVarMapNotFoundException {
    return environment.getVariable(key);
  }
}
