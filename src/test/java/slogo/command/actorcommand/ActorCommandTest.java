package slogo.command.actorcommand;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.general.Command.VAR_NAME_KEY;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.actorcommand.move.absolute.AbsoluteDistance;
import slogo.command.actorcommand.move.absolute.AbsoluteMove;
import slogo.command.actorcommand.move.absolute.AbsoluteTurn;
import slogo.command.actorcommand.move.relative.RelativeDistance;
import slogo.command.actorcommand.move.relative.RelativeTurn;
import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.ActorNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.model.World;

class ActorCommandTest {

  private List<Command> parameters;
  private World world;
  private Map<String, String> impliedParameters;
  private final double TOLERANCE = 0.001;

  @BeforeEach
  void setUp() {
    parameters = new ArrayList<>();
    world = new World();
    world.addActor(new Actor("test"));
    impliedParameters = new HashMap<>();
  }

  @Test
  void testRelativeMoveHappy() throws CommandException {
    RelativeDistance move = new RelativeDistance(parameters);
    RelativeTurn turn = new RelativeTurn(parameters);

    impliedParameters.put(VAR_NAME_KEY, "0");
    parameters.add(new GenericValue(10.0));
    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);

    assertEquals(10.0, move.execute(world, null).returnVal(), TOLERANCE);
    assertEquals(10.0, turn.execute(world, null).returnVal(), TOLERANCE);

    impliedParameters.put(VAR_NAME_KEY, "test");
    assertEquals(10.0, move.execute(world, null).returnVal(), TOLERANCE);
    assertEquals(10.0, turn.execute(world, null).returnVal(), TOLERANCE);
  }

  @Test
  void testRelativeMoveSad() {
    RelativeDistance move = new RelativeDistance(parameters);
    RelativeTurn turn = new RelativeTurn(parameters);

    assertThrows(ImpliedParametersNotSetException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParametersNotSetException.class, () -> turn.execute(null, null));

    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);
    impliedParameters.put(VAR_NAME_KEY, "1");

    assertThrows(ImpliedParameterNotFoundException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParameterNotFoundException.class, () -> turn.execute(null, null));

    assertThrows(ActorNotFoundException.class, () -> move.execute(world, null));
    assertThrows(ActorNotFoundException.class, () -> turn.execute(world, null));

    impliedParameters.put(VAR_NAME_KEY, "0");

    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));

    parameters.add(new GenericValue(5.0));
    parameters.add(new GenericValue(5.0));
    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));
  }

  @Test
  void testAbsoluteMoveSad() {
    AbsoluteDistance move = new AbsoluteDistance(parameters);
    AbsoluteTurn turn = new AbsoluteTurn(parameters);

    assertThrows(ImpliedParametersNotSetException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParametersNotSetException.class, () -> turn.execute(null, null));

    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);
    impliedParameters.put(VAR_NAME_KEY, "1");

    assertThrows(ImpliedParameterNotFoundException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParameterNotFoundException.class, () -> turn.execute(null, null));

    assertThrows(ActorNotFoundException.class, () -> move.execute(world, null));
    assertThrows(ActorNotFoundException.class, () -> turn.execute(world, null));

    impliedParameters.put(VAR_NAME_KEY, "0");

    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));

    parameters.add(new GenericValue(5.0));
    parameters.add(new GenericValue(5.0));
    parameters.add(new GenericValue(5.0));
    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));
  }

  @Test
  void testAbsoluteMoveHappy() throws CommandException {
    AbsoluteDistance move = new AbsoluteDistance(parameters);
    AbsoluteTurn turn = new AbsoluteTurn(parameters);

    impliedParameters.put(VAR_NAME_KEY, "0");
    parameters.add(new GenericValue(3.0));
    parameters.add(new GenericValue(4.0));
    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);

    assertEquals(5.0, move.execute(world, null).returnVal(), TOLERANCE);
    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    assertEquals(0.927, turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    impliedParameters.put(VAR_NAME_KEY, "test");
    assertEquals(5.0, move.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    assertEquals(0.927, turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    parameters.clear();
    parameters.add(new GenericValue(-3.0));
    parameters.add(new GenericValue(4.0));
    assertEquals(2.214, turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    parameters.clear();
    parameters.add(new GenericValue(-3.0));
    parameters.add(new GenericValue(-4.0));
    assertEquals(4.069, turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    parameters.clear();
    parameters.add(new GenericValue(3.0));
    parameters.add(new GenericValue(-4.0));
    assertEquals(0.927, turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor("test"));
    parameters.clear();
    parameters.add(new GenericValue(0.0));
    parameters.add(new GenericValue(0.0));
    assertEquals(0.0, turn.execute(world, null).returnVal(), TOLERANCE);
  }
}