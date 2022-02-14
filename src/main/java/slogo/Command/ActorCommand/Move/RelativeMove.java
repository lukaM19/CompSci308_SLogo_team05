package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.ActorCommand.Move.Move;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class RelativeMove extends Move {
  public static final int RELATIVE_MOVE_PARAM_NUMBER = 1;
  public static final int RAW_VAL_INDEX = 0;

  protected double rawValue;
  protected AbsoluteMove absoluteMoveCommand;

  public RelativeMove(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters);
    checkForCorrectParameterLength(RELATIVE_MOVE_PARAM_NUMBER);
    Command rawCommand = this.parameters.get(RAW_VAL_INDEX);

    try {
      rawValue = (Double) rawCommand.execute();
    } catch (Exception e) {
      throw new WrongParameterTypeException(getCommandName() + rawCommand);
    }
  }
}
