package slogo.command.actor;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.actor.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.actor.ActorCommand.SCALE_KEY;
import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Turtle.PEN_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.actor.attributes.ActorQuery;
import slogo.command.actor.attributes.ActorSetter;
import slogo.command.actor.move.absolute.PointDistance;
import slogo.command.actor.move.absolute.PointTurn;
import slogo.command.actor.move.relative.ValueDistance;
import slogo.command.actor.move.relative.ValueTurnRelative;
import slogo.command.exception.CommandException;
import slogo.command.exception.actorexception.ActorNotFoundException;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.model.Turtle;
import slogo.model.World;

class ActorCommandTest {

  private List<Command> parameters;
  private World world;
  private Map<String, String> impliedParameters;
  private final double TOLERANCE = 0.1;

  @BeforeEach
  void setUp() {
    parameters = new ArrayList<>();
    world = new World();
    world.addActor(new Turtle(0));
    impliedParameters = new HashMap<>();
  }

  @Test
  void testAttributeHappy() throws CommandException {
    ActorQuery actorQuery = new ActorQuery(parameters);
    ActorSetter actorSetter = new ActorSetter(parameters);
    actorQuery.setImpliedParameters(impliedParameters);
    actorSetter.setImpliedParameters(impliedParameters);

    impliedParameters.put(ACTOR_ID_KEY, "0");
    impliedParameters.put(VAR_NAME_KEY, PEN_KEY);
    impliedParameters.put(VAR_VALUE_KEY, "0.0");

    world.getActorByIndex(0).putVal(PEN_KEY, 0.0);

    assertEquals(0.0, actorQuery.execute(world, null).returnVal());
    assertEquals(0.0, actorSetter.execute(world, null).returnVal());
    assertEquals(0.0, world.getActorByIndex(0).getVal(PEN_KEY));

    impliedParameters.put(VAR_NAME_KEY, VISIBILITY_KEY);
    impliedParameters.put(VAR_VALUE_KEY, "0.0");

    assertEquals(1.0, actorQuery.execute(world, null).returnVal());
    assertEquals(0.0, actorSetter.execute(world, null).returnVal());
    assertEquals(0.0, world.getActorByIndex(0).getVal(VISIBILITY_KEY));
  }

  @Test
  void testAttributeSad() {
    ActorQuery actorQuery = new ActorQuery(parameters);
    ActorSetter actorSetter = new ActorSetter(parameters);

    assertThrows(ImpliedParametersNotSetException.class, () -> actorQuery.execute(world, null));
    assertThrows(ImpliedParametersNotSetException.class, () -> actorSetter.execute(world, null));

    actorQuery.setImpliedParameters(impliedParameters);
    actorSetter.setImpliedParameters(impliedParameters);
    impliedParameters.put(ACTOR_ID_KEY, "1");

    assertThrows(ActorNotFoundException.class, () -> actorQuery.execute(world, null));
    assertThrows(ActorNotFoundException.class, () -> actorSetter.execute(world, null));

    impliedParameters.put(ACTOR_ID_KEY, "0");
    impliedParameters.put(VAR_NAME_KEY, "fdjhsk");
    impliedParameters.put(VAR_VALUE_KEY, "0.0");

    assertThrows(UnknownActorValueException.class, () -> actorQuery.execute(world, null));
    assertThrows(UnknownActorValueException.class, () -> actorSetter.execute(world, null));

    impliedParameters.put(VAR_NAME_KEY, "pen");
    impliedParameters.put(VAR_VALUE_KEY, "the");
    assertThrows(WrongImpliedParameterTypeException.class, () -> actorSetter.execute(world, null));
  }

  @Test
  void testRelativeMoveHappy() throws CommandException {
    ValueDistance move = new ValueDistance(parameters);
    ValueTurnRelative turn = new ValueTurnRelative(parameters);

    impliedParameters.put("scale", "1");
    impliedParameters.put(ACTOR_ID_KEY, "0");
    parameters.add(new GenericValue(10.0));
    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);

    assertEquals(10.0, move.execute(world, null).returnVal(), TOLERANCE);
    assertEquals(10.0, turn.execute(world, null).returnVal(), TOLERANCE);
  }

  @Test
  void testRelativeMoveSad() {
    ValueDistance move = new ValueDistance(parameters);
    ValueTurnRelative turn = new ValueTurnRelative(parameters);

    assertThrows(ImpliedParametersNotSetException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParametersNotSetException.class, () -> turn.execute(null, null));

    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);
    impliedParameters.put(ACTOR_ID_KEY, "1");

    assertThrows(ImpliedParameterNotFoundException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParameterNotFoundException.class, () -> turn.execute(null, null));

    assertThrows(ActorNotFoundException.class, () -> move.execute(world, null));
    assertThrows(ActorNotFoundException.class, () -> turn.execute(world, null));

    impliedParameters.put(ACTOR_ID_KEY, "0");

    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));

    parameters.add(new GenericValue(5.0));
    parameters.add(new GenericValue(5.0));
    assertThrows(WrongParameterNumberException.class, () -> move.execute(world, null));
    assertThrows(WrongParameterNumberException.class, () -> turn.execute(world, null));
  }

  @Test
  void testAbsoluteMoveSad() {
    PointDistance move = new PointDistance(parameters);
    PointTurn turn = new PointTurn(parameters);

    assertThrows(ImpliedParametersNotSetException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParametersNotSetException.class, () -> turn.execute(null, null));

    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);
    impliedParameters.put(ACTOR_ID_KEY, "1");

    assertThrows(ImpliedParameterNotFoundException.class, () -> move.execute(null, null));
    assertThrows(ImpliedParameterNotFoundException.class, () -> turn.execute(null, null));

    assertThrows(ActorNotFoundException.class, () -> move.execute(world, null));
    assertThrows(ActorNotFoundException.class, () -> turn.execute(world, null));

    impliedParameters.put(ACTOR_ID_KEY, "0");

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
    PointDistance move = new PointDistance(parameters);
    PointTurn turn = new PointTurn(parameters);

    impliedParameters.put(ACTOR_ID_KEY, "0");
    impliedParameters.put(SCALE_KEY, "1");
    parameters.add(new GenericValue(3.0));
    parameters.add(new GenericValue(4.0));
    move.setImpliedParameters(impliedParameters);
    turn.setImpliedParameters(impliedParameters);

    assertEquals(5.0, move.execute(world, null).returnVal(), TOLERANCE);
    world.removeActorAt(0);
    world.addActor(new Actor(0));
    assertEquals(Math.toDegrees(0.927), turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    impliedParameters.put(VAR_NAME_KEY, "0");
    assertEquals(5.0, move.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    assertEquals(Math.toDegrees(0.927), turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    parameters.clear();
    parameters.add(new GenericValue(-3.0));
    parameters.add(new GenericValue(4.0));
    assertEquals(Math.toDegrees(2.214), turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    parameters.clear();
    parameters.add(new GenericValue(-3.0));
    parameters.add(new GenericValue(-4.0));
    assertEquals(Math.toDegrees(4.069), turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    parameters.clear();
    parameters.add(new GenericValue(3.0));
    parameters.add(new GenericValue(-4.0));
    assertEquals(Math.toDegrees(0.927), turn.execute(world, null).returnVal(), TOLERANCE);

    world.removeActorAt(0);
    world.addActor(new Actor(0));
    parameters.clear();
    parameters.add(new GenericValue(0.0));
    parameters.add(new GenericValue(0.0));
    assertEquals(0.0, turn.execute(world, null).returnVal(), TOLERANCE);
  }
}