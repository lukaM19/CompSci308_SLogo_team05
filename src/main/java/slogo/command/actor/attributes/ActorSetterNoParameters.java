package slogo.command.actor.attributes;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Turtle.PEN_STATE_KEY;

import java.util.List;
import slogo.command.actor.ActorCommand;

import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;
import slogo.command.value.GenericValue;
import slogo.model.Actor;

@SlogoCommand(keywords = {"PenDown", "PenUp", "ShowTurtle", "HideTurtle"})
@ImpliedArgument(keywords = {"PenDown", "PenUp"}, arg = VAR_NAME_KEY, value = PEN_STATE_KEY)
@ImpliedArgument(keywords = {"ShowTurtle", "HideTurtle"}, arg = VAR_NAME_KEY, value = VISIBILITY_KEY)
@ImpliedArgument(keywords = {"PenUp", "HideTurtle"}, arg = VAR_VALUE_KEY, value = "0")
@ImpliedArgument(keywords = {"PenDown", "ShowTurtle"}, arg = VAR_VALUE_KEY, value = "1")


public class ActorSetterNoParameters extends ActorCommand {

  private String key;
  private Command newVal;

  /***
   * Creates a Command that acts on an actor(s)
   *
   * @param parameters - parameters for command
   */
  public ActorSetterNoParameters(
      List<Command> parameters) {
    super(parameters);
  }

  /***
   * Assigns private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  protected void assignSetterVariables()
      throws CommandException {
    try {
      key = getImpliedParameter(VAR_NAME_KEY);
      newVal = getParameterCommand(0);
      newVal = new GenericValue(Double.parseDouble(getImpliedParameter(VAR_VALUE_KEY)));
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
