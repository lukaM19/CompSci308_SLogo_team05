package slogo.command.general;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.model.World;

public abstract class Command {
  public static final CommandResult DEFAULT_VALUE = new CommandResult(0.0, Optional.empty());
  public static final String VAR_NAME_KEY = "name";
  public static final String VAR_VALUE_KEY = "value";

  protected List<Command> parameters;
  protected Map<String, String> impliedParameters;
  private final String commandName;

  /***
   * Command object used by interpreter to execute various actions
   *
   * @param parameters - the parameters for command
   */
  public Command(List<Command> parameters) {
    this.commandName = this.getClass().getSimpleName() + ": ";
    this.parameters = parameters;
  }

  /***
   * Sets up implied parameters list
   *
   * @param impliedParameters is the list of implied parameters
   */
  public final void setImpliedParameters(Map<String, String> impliedParameters) {
    this.impliedParameters = impliedParameters;
  }

  protected final String getImpliedParameter(String parameterName) throws ImpliedParameterException {
    if(impliedParameters == null) throw new ImpliedParametersNotSetException(getCommandName());
    if(!impliedParameters.containsKey(parameterName)) throw new ImpliedParameterNotFoundException(getCommandName() + impliedParameters);
    return impliedParameters.get(parameterName);
  }

  /***
   * Gets the name of the current command object
   *
   * @return command class name
   */
  protected final String getCommandName() {
    return commandName;
  }

  /***
   * Gets size of the parameters list
   *
   * @return size of parameters list
   */
  protected final int getParametersSize() {
    return parameters.size();
  }

  /***
   * Checks if the parameter size is the same as expected
   *
   * @param desiredSize is the correct size for the parameter list
   * @throws WrongParameterNumberException if the sizes are mismatched
   */
  protected final void checkForExactParameterLength(int desiredSize)
      throws WrongParameterNumberException {
    if(getParametersSize() != desiredSize) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Checks if the parameter size is at least as expected
   *
   * @param minSize is the minimum size for the parameter list
   * @throws WrongParameterNumberException if minSize is larger than the length of the parameter list
   */
  protected final void checkForMinParameterLength(int minSize) throws WrongParameterNumberException {
    if(getParametersSize() < minSize) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Sets up parameters prior to execution
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  protected abstract void setUpExecution(World world, Map<String, Double> userVars) throws CommandException;

  /***
   * Runs command after setup
   *
   * @return return value of command
   * @throws CommandException if command cannot be executed
   */
  protected abstract CommandResult run() throws CommandException;

  /***
   * Executes a given command
   *
   * @return result of execution
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  public final CommandResult execute(World world, Map<String, Double> userVars) throws CommandException {
    setUpExecution(world, userVars);
    return run();
  }
}
