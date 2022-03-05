package slogo.command.actor.attributes;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.model.Turtle.PEN_COLOR_KEY;
import static slogo.model.Turtle.PEN_SIZE_KEY;
import static slogo.model.Turtle.SHAPE_KEY;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SetPenColor", "SetShape", "SetPenSize"})
@ImpliedArgument(keywords = {"SetPenColor"}, arg = VAR_NAME_KEY, value = PEN_COLOR_KEY)
@ImpliedArgument(keywords = {"SetShape"}, arg = VAR_NAME_KEY, value = SHAPE_KEY)
@ImpliedArgument(keywords = {"SetPenSize"}, arg = VAR_NAME_KEY, value = PEN_SIZE_KEY)

public class ActorSetterOneParameter extends ActorSetterNoParameters {

  private static final int PARAM_INDEX = 0;
  private static final int PARAM_NUM = 1;

  private String key;
  private Command newVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public ActorSetterOneParameter(
      List<Command> parameters) {
    super(parameters);
    setParamNumber(PARAM_NUM);
  }

  /***
   * Assigns private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void assignSetterVariables()
      throws CommandException {
    try {
      key = getImpliedParameter(VAR_NAME_KEY);
      newVal = getParameterCommand(PARAM_INDEX);
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
    }
  }
}
