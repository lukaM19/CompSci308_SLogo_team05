package slogo.command.actorcommand.attributes;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.ActorCommand;
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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Setter(World world,
      List<Command> parameters,
      Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException, UnknownActorValueException {

    super(world, parameters, userVars);
    checkForExactParameterLength(SETTER_PARAMETER_NUMBER);
    assignSetterVariables();
    if(!actor.hasVal(key)) {
      throw new UnknownActorValueException(getCommandName() + key);
    }
  }

  private void assignSetterVariables() throws WrongParameterTypeException {
    Object setterValues = this.parameters.get(SETTER_VALUES_INDEX).execute();
    try {
      SetterParameters setterParameters = (SetterParameters) setterValues;
      key = setterParameters.key();
      newVal = setterParameters.newVal();
      returnVal = setterParameters.returnVal();
    } catch (Exception e) {
      throw new WrongParameterTypeException(getCommandName() + setterValues);
    }
  }

  @Override
  public Object execute() {
    actor.putVal(key, newVal);
    return returnVal;
  }
}
