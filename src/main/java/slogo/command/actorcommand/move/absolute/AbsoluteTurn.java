package slogo.command.actorcommand.move.absolute;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public class AbsoluteTurn extends AbsoluteMove{

  public static final double FULL_ROTATION = Math.PI * 2.0;
  public static final double NINETY_DEGREES = Math.PI / 2.0;
  public static final double ZERO = 0.0;

  private double newAngle;

  /***
   * Creates an AbsoluteMove Command that turns the actor towards a given point
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteTurn(World world,
      List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * Calculates angle given a point using arctan and angle adjustments
   */
  @Override
  protected void calculateMovement() {
    double yDiff = coords[Y_INDEX] - actor.getPosition().getY();
    double xDiff = coords[X_INDEX] - actor.getPosition().getX();

    if(xDiff == ZERO) {
      newAngle = actor.getHeading();
    }
    else {
      double angleRelativeToXAxis =
          Math.atan(yDiff / xDiff) + (xDiff < ZERO ? NINETY_DEGREES : ZERO);
      newAngle = yDiff < ZERO ? angleRelativeToXAxis : FULL_ROTATION - angleRelativeToXAxis;
    }
  }

  /***
   * Sets the actor's angle and then returns the degrees turned
   *
   * @return angle turned
   */
  @Override
  public Object execute() {
    double prevHeading = actor.getHeading();
    actor.setHeading(newAngle);
    return newAngle - prevHeading;
  }
}
