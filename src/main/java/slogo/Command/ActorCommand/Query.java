package slogo.Command.ActorCommand;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.UnknownActorValueException;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class Query extends ActorCommand{

  protected static final int QUERY_PARAMETER_NUMBER = 1;

  protected String queryVar;

  public Query(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException, UnknownActorValueException {
    super(world, parameters);
    if(!correctParameterLength(QUERY_PARAMETER_NUMBER)) {
      throw new WrongParameterNumberException(getCommandName() + this.parameters.size());
    }
    setQueryVar(this.parameters.get(0));
  }

  private void setQueryVar(Command queryVarWrapper) throws UnknownActorValueException {
    queryVar = queryVarWrapper.execute().toString();
    if(!actor.hasVal(queryVar)) {
      throw new UnknownActorValueException(getCommandName() + queryVar);
    }
  }

  @Override
  public Object execute() {
    return actor.getVal(queryVar);
  }
}
