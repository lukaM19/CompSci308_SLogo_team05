package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.Command.GenericValue;
import slogo.model.World;

public class DistanceMove extends RelativeMove{

  public DistanceMove(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  @Override
  protected void calculateMovement()
      throws WrongParameterTypeException, WrongParameterNumberException {
    double angle = actor.getHeading();
    double newX = rawValue*Math.cos(angle);
    double newY = rawValue*Math.sin(angle);
    absoluteMoveCommand = new AbsoluteDistanceMove(world, List.of(new GenericValue(newX), new GenericValue(newY)));
  }

  @Override
  public Object execute() {
    return absoluteMoveCommand.execute();
  }
}
