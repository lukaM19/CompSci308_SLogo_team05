package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class AbsoluteDistanceMove extends AbsoluteMove{

  public AbsoluteDistanceMove(World world,
      List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  @Override
  protected void calculateMovement() {

  }

  @Override
  public Object execute() {
    return null;
  }
}
