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
import slogo.model.PaletteWrapper;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SetPalette"}, arguments = 4)
@ImpliedArgument(keywords = {"SetPalette"}, arg = VAR_NAME_KEY, value = PALETTE_KEY)

public class SetPalette extends WorldSetter {

  private static final int PARAM_NUM = 4;
  private static final int INDEX_INDEX = 0;
  private static final int R_INDEX = 0;
  private static final int G_INDEX = 0;
  private static final int B_INDEX = 0;

  private String key;
  private List<Command> newVals;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public SetPalette(
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
      newVals = List.of(getParameterCommand(INDEX_INDEX), getParameterCommand(R_INDEX),
          getParameterCommand(G_INDEX), getParameterCommand(B_INDEX));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(
          getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
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
    if (!getWorld().hasKey(key)) {
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
    PaletteWrapper paletteWrapper = new PaletteWrapper(
        newVals.get(INDEX_INDEX).execute(getWorld(), getUserVars()).returnVal(),
        newVals.get(R_INDEX).execute(getWorld(), getUserVars()).returnVal(),
        newVals.get(G_INDEX).execute(getWorld(), getUserVars()).returnVal(),
        newVals.get(B_INDEX).execute(getWorld(), getUserVars()).returnVal());
    return paletteWrapper.index();
  }
}
