package slogo.command.value;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.model.World;

public class Assigment extends Command {

  public static final int ASSIGNMENT_PARAMETER_NUMBER = 2;
  public static final int KEY_INDEX = 0;
  public static final int VALUE_INDEX = 1;

  private String key;
  private Object value;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public Assigment(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException {

    super(world, parameters, userVars);
    checkForExactParameterLength(ASSIGNMENT_PARAMETER_NUMBER);
    key = parameters.get(KEY_INDEX).execute().toString();
    value = parameters.get(VALUE_INDEX).execute();
  }

  /***
   * Gets the key of this assignment
   *
   * @return key
   */
  public String getKey() {
    return key;
  }

  /***
   * Puts given key and value into the map
   *
   * @return value in map
   */
  @Override
  public Object execute() {
    userVars.put(key, value);
    return value;
  }
}
