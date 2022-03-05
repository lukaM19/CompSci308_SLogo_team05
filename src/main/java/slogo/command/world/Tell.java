package slogo.command.world;

import java.util.ArrayList;
import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.general.CommandResult;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Tell"}, arguments = 1)
public class Tell extends WorldCommand {

  private static final int PARAM_NUMBER = 1;
  private static final int PARAM_INDEX = 0;
  private Command idCommands;

  /***
   * Command object that acts on the World
   *
   * @param parameters - the parameters for command
   */
  public Tell(List<Command> parameters) {
    super(parameters);
    setParamNumber(PARAM_NUMBER);
  }

  @Override
  protected void setUpExecution() throws CommandException {
    idCommands = getParameterCommand(PARAM_INDEX);
  }

  @Override
  protected Double run() throws CommandException {
    double lastID = idCommands.execute(getWorld(), getUserVars()).returnVal();
    List<Double> ids = new ArrayList<>();
    if(idCommands instanceof CommandList) {
      for(CommandResult commandResult: ((CommandList) idCommands).getAllResults()) {
        ids.add(commandResult.returnVal());
      }
    } else{
      ids.add(lastID);
    }
    getWorld().setActiveActors(ids);
    return lastID;
  }
}
