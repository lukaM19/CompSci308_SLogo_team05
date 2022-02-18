package slogo.command.actorcommand.attributes;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.exception.UnknownActorValueException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public class Setter extends ActorCommand {

  public static final int SETTER_PARAMETER_NUMBER = 1;
  public static final int SETTER_VALUES_INDEX = 0;

  private String key;
  private Object newVal;
  private Object returnVal;

  /***
   * Creates a Command that acts on an actor
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Setter(
      List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException, UnknownActorValueException {

    super(parameters);
    checkForExactParameterLength(SETTER_PARAMETER_NUMBER);
  }

  /***
   * Assigns private instance variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  private void assignSetterVariables(World world, Map<String, Object> userVars)
      throws CommandException {
    Object setterValues = this.parameters.get(SETTER_VALUES_INDEX).execute(world, userVars);
    try {
      SetterParameters setterParameters = (SetterParameters) setterValues;
      key = setterParameters.key();
      newVal = setterParameters.newVal();
      returnVal = setterParameters.returnVal();
    } catch (Exception e) {
      throw new WrongParameterTypeException(getCommandName() + setterValues);
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
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    assignSetterVariables(world, userVars);
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
  public Object run() {
    actor.putVal(key, newVal);
    return returnVal;
  }
}
