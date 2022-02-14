package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.ActorCommand.ActorCommand;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class Move extends ActorCommand {

  public Move(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  protected abstract void calculateMovement()
      throws WrongParameterTypeException, WrongParameterNumberException;
}
