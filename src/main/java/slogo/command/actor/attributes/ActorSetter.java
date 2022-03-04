package slogo.command.actor.attributes;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Turtle.PEN_COLOR_KEY;
import static slogo.model.Turtle.PEN_KEY;
import static slogo.model.Turtle.PEN_SIZE_KEY;
import static slogo.model.Turtle.SHAPE_KEY;

import java.util.List;
import slogo.command.actor.ActorCommand;
import java.util.Map;

import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"PenDown", "PenUp", "ShowTurtle", "HideTurtle", "SetPenColor", "SetShape", "SetPenSize"})
@ImpliedArgument(keywords = {"PenDown", "PenUp"}, arg = VAR_NAME_KEY, value = PEN_KEY)
@ImpliedArgument(keywords = {"SetPenColor"}, arg = VAR_NAME_KEY, value = PEN_COLOR_KEY)
@ImpliedArgument(keywords = {"SetShape"}, arg = VAR_NAME_KEY, value = SHAPE_KEY)
@ImpliedArgument(keywords = {"SetPenSize"}, arg = VAR_NAME_KEY, value = PEN_SIZE_KEY)
@ImpliedArgument(keywords = {"ShowTurtle", "HideTurtle"}, arg = VAR_NAME_KEY, value = VISIBILITY_KEY)
@ImpliedArgument(keywords = {"PenUp", "HideTurtle"}, arg = VAR_VALUE_KEY, value = "0")
@ImpliedArgument(keywords = {"PenDown", "ShowTurtle"}, arg = VAR_VALUE_KEY, value = "1")


public class ActorSetter extends ActorCommand {

  private String key;
  private Command newVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public ActorSetter(
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
      //TODO: FIX THIS BY USING WORLD AND USERVARS
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
    for(Actor actor: getActors()) {
      if (!actor.hasVal(key)) {
        throw new UnknownActorValueException(getCommandName() + key);
      }
    }
  }

  /***
   * Sets given variable to desired value and returns return value
   *
   * @return given return value
   */
  @Override
  public Double run() throws CommandException {
    Double lastVal = DEFAULT_VALUE;
    for(Actor actor: getActors()) {
      lastVal = newVal.execute(getWorld(), getUserVars()).returnVal();
      actor.putVal(key, lastVal);
    }
    return lastVal;
  }
}
