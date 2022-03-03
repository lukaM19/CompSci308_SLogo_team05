package slogo.command.actorcommand.attributes;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Turtle.PEN_KEY;

import java.util.List;
import java.util.Map;

import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.model.Environment;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"PenDown", "PenUp", "ShowTurtle", "HideTurtle"})
@ImpliedArgument(keywords = {"PenDown", "PenUp", "ShowTurtle", "HideTurtle"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"PenDown", "PenUp"}, arg = VAR_NAME_KEY, value = PEN_KEY)
@ImpliedArgument(keywords = {"ShowTurtle", "HideTurtle"}, arg = VAR_NAME_KEY, value = VISIBILITY_KEY)
@ImpliedArgument(keywords = {"PenUp", "HideTurtle"}, arg = VAR_VALUE_KEY, value = "0")
@ImpliedArgument(keywords = {"PenDown", "ShowTurtle"}, arg = VAR_VALUE_KEY, value = "1")
public class Setter extends ActorCommand {

  private String key;
  private Double newVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   */
  public Setter(
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
      newVal = Double.parseDouble(getImpliedParameter(VAR_VALUE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
    }
  }

  /***
   * Sets up setter variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    assignSetterVariables();
    if(!actor.hasVal(key)) {
      throw new UnknownActorValueException(getCommandName() + key);
    }
  }

  /***
   * Sets given variable to desired value and returns return value
   *
   * @return given return value
   */
  @Override
  public Double run() {
    actor.putVal(key, newVal);
    return newVal;
  }
}
