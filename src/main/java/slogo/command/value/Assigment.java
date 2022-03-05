package slogo.command.value;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"MakeVariable"}, arguments = 2)
public class Assigment extends Command {

  private String key;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param parameters - parameters for command
   */
  public Assigment(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up key value pair
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    checkForExactParameterLength(2);
    try {
      this.key = ((UserValue)getParameterCommand(0)).getVarName();
    } catch (ClassCastException e) {
     throw new WrongParameterTypeException(getCommandName() + " - " + 0);
    }
  }

  /***
   * Puts value in userVar map
   *
   * @return value in userVar map
   */
  @Override
  protected Double run() throws CommandException {
    if(userVars == null) {
      throw new UserVarMapNotFoundException(getCommandName());
    }
    double value = executeParameter(1, world, userVars).returnVal();
    userVars.put(key, value);
    return value;
  }
}
