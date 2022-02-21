package slogo.command.math;

import static org.junit.jupiter.api.Assertions.*;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.math.basicoperation.Log;
import slogo.command.math.basicoperation.Minus;
import slogo.command.math.basicoperation.Product;
import slogo.command.math.basicoperation.Sum;
import slogo.command.math.random.Random;
import slogo.command.math.trig.Arctangent;
import slogo.command.math.trig.Cosine;
import slogo.command.math.trig.Sine;
import slogo.command.math.trig.Tangent;
import slogo.command.value.GenericValue;

class FunctionTest {
  private List<Command> parameters;
  private Command parameter;
  private Command parameterTwo;
  private Math command;
  private final double TOLERANCE = 0.01;

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    parameter = new GenericValue(10.0);
    parameterTwo = new GenericValue(5.0);
  }

  @Test
  void testTrigHappy() {
    parameters.add(parameter);

    try {
      command = new Arctangent(parameters);
      assertEquals(1.47, command.execute(null, null).returnVal(), TOLERANCE);

      command = new Cosine(parameters);
      assertEquals(-0.84, command.execute(null, null).returnVal(), TOLERANCE);

      command = new Sine(parameters);
      assertEquals(-0.54, command.execute(null, null).returnVal(), TOLERANCE);

      command = new Tangent(parameters);
      assertEquals(0.65, command.execute(null, null).returnVal(), TOLERANCE);
    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testOtherFunctionsHappy() {
    parameters.add(parameter);

    try {
      command = new Minus(parameters);
      assertEquals(-10.0, command.execute(null, null).returnVal());

      command = new Log(parameters);
      assertEquals(2.30, command.execute(null, null).returnVal(), TOLERANCE);

      command = new Random(parameters);
      double randomVal = command.execute(null, null).returnVal();
      if(randomVal >= 0d && randomVal < 10d) {
        return;
      } else {
        fail();
      }

    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testTrigSad() {
    assertThrows(WrongParameterNumberException.class, () -> new Arctangent(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Cosine(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Sine(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Tangent(parameters));

    parameters.add(parameter);
    parameters.add(parameterTwo);
    assertThrows(WrongParameterNumberException.class, () -> new Arctangent(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Cosine(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Sine(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Tangent(parameters));
  }

  @Test
  void testOtherFunctionsSad() {
    assertThrows(WrongParameterNumberException.class, () -> new Minus(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Random(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Log(parameters));

    parameters.add(parameter);
    parameters.add(parameterTwo);
    assertThrows(WrongParameterNumberException.class, () -> new Minus(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Random(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Log(parameters));
  }
}