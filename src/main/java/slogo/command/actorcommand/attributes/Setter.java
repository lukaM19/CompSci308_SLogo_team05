package slogo.command.actorcommand.attributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class Setter extends ActorCommand {

  private String key;
  private Double newVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public Setter(
      List<Command> parameters)
      throws WrongParameterNumberException {
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
      throw new WrongParameterTypeException(getCommandName() + getImpliedParameter(VAR_VALUE_KEY));
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
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
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
