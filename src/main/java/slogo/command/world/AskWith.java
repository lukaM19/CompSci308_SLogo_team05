package slogo.command.world;

import static slogo.command.logic.Logic.ACCEPTED_VALUES;

import java.util.ArrayList;
import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.World;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"AskWith"}, arguments = 2)
public class AskWith extends WorldCommand {

  private final static int PARAM_NUMBER = 2;
  private final static int CONDITION_PARAM_INDEX = 0;
  private final static int BODY_PARAM_INDEX = 1;

  private Command condition;
  private Command bodyCommand;

  /***
   * Command object that acts on the World
   *
   * @param parameters - the parameters for command
   */
  public AskWith(List<Command> parameters) {
    super(parameters);
    setParamNumber(PARAM_NUMBER);
  }

  @Override
  protected void setUpExecution() throws CommandException {
    condition = getParameterCommand(CONDITION_PARAM_INDEX);
    bodyCommand = getParameterCommand(BODY_PARAM_INDEX);
  }

  @Override
  protected Double run() throws CommandException {
    double lastCommandRet = DEFAULT_VALUE;
    List<Double> oldActiveActorIDs = getWorld().getActiveActorIds();
    for(double actorID: oldActiveActorIDs) {
      getWorld().setActiveActors(new ArrayList<>());
      getWorld().setActiveActors(List.of(actorID));
      if(ACCEPTED_VALUES.get(condition.execute(getWorld(), getUserVars()).returnVal())) {
        lastCommandRet = bodyCommand.execute(getWorld(), getUserVars()).returnVal();
      }
    }
    getWorld().setActiveActors(oldActiveActorIDs);
    return lastCommandRet;
  }
}
