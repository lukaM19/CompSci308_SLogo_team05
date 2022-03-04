package slogo.command.world;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.model.World.BACKGROUND_KEY;
import static slogo.model.World.PALETTE_KEY;

import java.util.List;
import slogo.command.actor.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.exception.worldexception.UnknownWorldValueException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SetBackground", "SetPalette"}, arguments = 1)
@ImpliedArgument(keywords = {"SetBackground"}, arg = VAR_NAME_KEY, value = BACKGROUND_KEY)
@ImpliedArgument(keywords = {"SetPalette"}, arg = VAR_NAME_KEY, value = PALETTE_KEY)

public class WorldSetter extends Command {

  private static final int PARAM_NUM = 1;
  private static final int PARAM_INDEX = 0;

  private String key;
  private Command newVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public WorldSetter(
      List<Command> parameters) {
    super(parameters);
    setParamNumber(PARAM_NUM);
  }

  /***
   * Assigns private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  private void assignSetterVariables()
      throws CommandException {
    try {
      key = getImpliedParameter(VAR_NAME_KEY);
      newVal = getParameterCommand(PARAM_INDEX);
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
    }
  }

  /***
   * Sets up setter variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    assignSetterVariables();
    if(!getWorld().hasKey(key)) {
      throw new UnknownWorldValueException(getCommandName() + key);
    }
  }

  /***
   * Sets given variable to desired value and returns return value
   *
   * @return given return value
   */
  @Override
  public Double run() throws CommandException {
    double retVal = newVal.execute(getWorld(), getUserVars()).returnVal();
    getWorld().putVal(key, retVal);
    return retVal;
  }
}
