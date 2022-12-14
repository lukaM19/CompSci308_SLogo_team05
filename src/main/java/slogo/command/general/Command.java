package slogo.command.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.model.MoveInfo;
import slogo.model.World;

public abstract class Command {
  public static final double DEFAULT_VALUE = 0d;
  public static final String VAR_NAME_KEY = "name";
  public static final String VAR_VALUE_KEY = "value";
  public static final String TEMP_FIX_KEY = "NOTHING";

  private int paramNumber;
  private boolean isMin;

  private final List<MoveInfo> moveInfos = new ArrayList<>();
  private List<Command> parameters;
  private final String commandName;

  private Map<String, String> impliedParameters;
  private World world;
  private Map<String, Double> userVars;

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
   * Sets the number of parameters
   *
   * @param paramNumber is the number of parameters
   */
  protected final void setMinParamNumber(int paramNumber) {
    this.paramNumber = paramNumber;
    this.isMin = true;
  }

  /***
   * Sets the minimum number of parameters
   *
   * @param paramNumber is the minimum number of parameters
   */
  protected final void setParamNumber(int paramNumber) {
    this.paramNumber = paramNumber;
    this.isMin = false;
  }

  /***
   * Sets up implied parameters list
   *
   * @param impliedParameters is the list of implied parameters
   */
  public final void setImpliedParameters(Map<String, String> impliedParameters) {
    this.impliedParameters = impliedParameters;
  }

  /**
   * Executes the parameter at the specific index and returns the result
   * Where possible, this method is always preferred to getParameterCommand()
   * @param index the index of the parameter to execute
   * @return the result of running the parameter command
   * @throws CommandException if the parameter throws a CommandException
   */
  protected CommandResult executeParameter(int index) throws CommandException {
    CommandResult res = parameters.get(index).execute(getWorld(), getUserVars());
    mergeMoveInfos(res.moveInfos());
    return res;
  }

  /**
   * Executes the command provided and returns the result
   * Where possible, this method is always preferred to directly calling the execute method on the command
   * @param cmd the command to execute
   * @return the result of running the command
   * @throws CommandException if the command throws a CommandException
   */
  protected CommandResult executeCommand(Command cmd) throws CommandException {
    CommandResult res = cmd.execute(world, userVars);
    mergeMoveInfos(res.moveInfos());
    return res;
  }

  protected Command getParameterCommand(int index) {
    return parameters.get(index);
  }

  protected void addMoveInfo(MoveInfo toAdd) {
    moveInfos.add(toAdd);
  }

  protected void mergeMoveInfos(List<MoveInfo> other) {
    moveInfos.addAll(other);
  }

  protected List<MoveInfo> getMoveInfos() {
    return moveInfos;
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
  public final int getParametersSize() {
    return parameters == null ? 0 : parameters.size();
  }

  /***
   * Checks if the parameter size is the same as expected
   *
   * @throws WrongParameterNumberException if the sizes are mismatched
   */
  private void checkForExactParameterLength()
      throws WrongParameterNumberException {
    if(getParametersSize() != paramNumber) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Checks if the parameter size is at least as expected
   *
   * @throws WrongParameterNumberException if minSize is larger than the length of the parameter list
   */
  private void checkForMinParameterLength() throws WrongParameterNumberException {
    if(getParametersSize() < paramNumber) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Sets up parameters prior to execution
   *
   * @throws CommandException if command cannot be executed
   */
  protected abstract void setUpExecution()
      throws CommandException;

  /***
   * Runs command after setup
   *
   * @return return value of command
   * @throws CommandException if command cannot be executed
   */
  protected abstract Double run() throws CommandException;

  /***
   * Executes a given command
   *
   * @return result of execution
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  public final CommandResult execute(World world, Map<String, Double> userVars) throws CommandException {
    moveInfos.clear();
    this.world = world;
    this.userVars = userVars;
    if(isMin) {
      checkForMinParameterLength();
    }
    else {
      checkForExactParameterLength();
    }

    setUpExecution();
    return new CommandResult(run(), getMoveInfos());
  }

  /***
   * @return implied parameters
   */
  protected Map<String, String> getImpliedParameters() {
    return impliedParameters;
  }

  /***
   * @return world
   */
  protected World getWorld() {
    return world;
  }

  /***
   * @return user vars
   */
  protected Map<String, Double> getUserVars() {
    return userVars;
  }
}
