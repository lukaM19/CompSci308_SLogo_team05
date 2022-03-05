package slogo.command.value;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
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
    setParamNumber(2);
  }

  /***
   * Sets up key value pair
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
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
    double value = executeParameter(1).returnVal();
    try {
        getUserVars().put(key, value);
    } catch (NullPointerException e) {
        throw new UserVarMapNotFoundException(getCommandName());
    }
    return value;
  }
}
