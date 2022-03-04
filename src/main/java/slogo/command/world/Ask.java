package slogo.command.world;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Ask"}, arguments = 2)
public class Ask extends Tell {

  private static final int PARAM_INDEX = 1;

  private List<Double> oldIds;
  private Command bodyCommand;

  /***
   * Command object that acts on the World
   *
   * @param parameters - the parameters for command
   */
  public Ask(List<Command> parameters) {
    super(parameters);
  }

  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    bodyCommand = getParameterCommand(PARAM_INDEX);
    oldIds = getWorld().getActiveActorIds();
  }

  @Override
  protected Double run() throws CommandException {
    super.run();
    double lastVal = executeCommand(bodyCommand).returnVal();
    getWorld().setActiveActors(oldIds);

    return lastVal;
  }
}
