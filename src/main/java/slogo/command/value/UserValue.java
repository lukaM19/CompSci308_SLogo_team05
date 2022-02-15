package slogo.command.value;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.model.World;

public class UserValue extends GenericValue {

  /***
   * Command object used by interpreter to execute various actions
   * @param world - the model to execute on
   * @param parameters - the parameters for command
   * @param userVars - the map of user variables
   */
  public UserValue(World world, List<Command> parameters,
      Map<String, Object> userVars) throws WrongParameterNumberException {
    super(world, parameters, userVars);
  }

  /***
   * Returns the corresponding value in the map of this Command
   *
   * @return map value, default value if not present
   */
  @Override
  public Object execute() {
    String key = value.toString();
    if(userVars.containsKey(key)) {
      return userVars.get(key);
    }
    return DEFAULT_VALUE;
  }
}
