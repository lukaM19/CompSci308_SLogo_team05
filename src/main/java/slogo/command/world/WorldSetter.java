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
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetBackground", "SetPalette"})
@ImpliedArgument(keywords = {"SetBackground"}, arg = VAR_NAME_KEY, value = BACKGROUND_KEY)
@ImpliedArgument(keywords = {"SetPalette"}, arg = VAR_NAME_KEY, value = PALETTE_KEY)

public class WorldSetter extends ActorCommand {

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
      if(getParametersSize() != 0) {
        newVal = getParameterCommand(0);
      }
      else {
        newVal = new GenericValue(Double.parseDouble(getImpliedParameter(VAR_VALUE_KEY)));
      }
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
    super.setUpExecution();
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
