package slogo.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.command.logic.operator.Not;
import slogo.command.math.basicoperation.Minus;
import slogo.command.math.comparator.Equals;
import slogo.command.math.comparator.Greater;
import slogo.command.math.comparator.GreaterEqual;
import slogo.command.math.comparator.Less;
import slogo.command.math.comparator.LessEqual;
import slogo.command.math.comparator.NotEquals;
import slogo.command.math.random.Random;
import slogo.command.math.trig.Arctangent;
import slogo.command.math.trig.Cosine;
import slogo.command.math.trig.Sine;
import slogo.command.math.trig.Tangent;
import slogo.command.value.GenericValue;

class ComparatorTest {
  private List<Command> parameters;
  private Command parameterOne;
  private Command parameterTwo;
  private Command parameterThree;
  private Math command;

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    parameterOne = new GenericValue(10.0);
    parameterTwo = new GenericValue(10.0);
    parameterThree = new GenericValue(11.0);
  }

  @Test
  void testComparatorHappy() {
    parameters.add(parameterOne);
    parameters.add(parameterTwo);

    try {
      command = new Equals(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new Greater(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());

      command = new GreaterEqual(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new Less(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());

      command = new LessEqual(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new NotEquals(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());


      parameters.clear();
      parameters.add(parameterTwo);
      parameters.add(parameterThree);

      command = new Equals(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());

      command = new GreaterEqual(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());

      command = new Less(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new NotEquals(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      parameters.clear();
      parameters.add(parameterThree);
      parameters.add(parameterTwo);

      command = new Greater(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new LessEqual(parameters);
      assertEquals(0.0, command.execute(null, null).returnVal());

    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testComparatorSad() {
    assertThrows(WrongParameterNumberException.class, () -> new Equals(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Greater(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new GreaterEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Less(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new LessEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new NotEquals(parameters));

    parameters.add(parameterOne);
    assertThrows(WrongParameterNumberException.class, () -> new Equals(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Greater(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new GreaterEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Less(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new LessEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new NotEquals(parameters));

    parameters.add(parameterOne);
    parameters.add(parameterTwo);
    parameters.add(parameterThree);
    assertThrows(WrongParameterNumberException.class, () -> new Equals(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Greater(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new GreaterEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Less(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new LessEqual(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new NotEquals(parameters));
  }
}