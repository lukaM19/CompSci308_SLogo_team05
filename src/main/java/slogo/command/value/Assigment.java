package slogo.command.value;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class Assigment extends Command {

  public static final int ASSIGNMENT_PARAMETER_NUMBER = 1;
  public static final int VALUE_INDEX = 0;

  private String key;
  private CommandResult value;
  private Map<String, Double> userVars;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param parameters - parameters for command
   */
  public Assigment(List<Command> parameters) throws WrongParameterNumberException {
    super(parameters);
    checkForExactParameterLength(ASSIGNMENT_PARAMETER_NUMBER);
  }

  /***
   * Gets the key of this assignment
   *
   * @return key
   */
  public String getKey() {
    return key;
  }

  /***
   * Sets up key value pair
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    this.key = getImpliedParameter(VAR_NAME_KEY);
    this.value = parameters.get(VALUE_INDEX).execute(world, userVars);
    this.userVars = userVars;
  }

  /***
   * Puts value in userVar map
   *
   * @return value in userVar map
   */
  @Override
  protected CommandResult run() {
    userVars.put(key, value.returnVal());
    return value;
  }
}
