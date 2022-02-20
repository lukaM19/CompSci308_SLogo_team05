package slogo.command.value;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class Assigment extends Command {

  private String key;
  private Double value;
  private Map<String, Double> userVars;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param parameters - parameters for command
   */
  public Assigment(List<Command> parameters) {
    super(parameters);
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
    try {
      this.value = Double.parseDouble(getImpliedParameter(VAR_VALUE_KEY));
    } catch (NumberFormatException e) {
     throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
    }
    this.key = getImpliedParameter(VAR_NAME_KEY);
    this.userVars = userVars;
  }

  /***
   * Puts value in userVar map
   *
   * @return value in userVar map
   */
  @Override
  protected Double run() {
    userVars.put(key, value);
    return value;
  }
}
