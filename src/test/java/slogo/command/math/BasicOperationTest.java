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
import slogo.command.math.basicoperation.Difference;
import slogo.command.math.basicoperation.Power;
import slogo.command.math.basicoperation.Product;
import slogo.command.math.basicoperation.Quotient;
import slogo.command.math.basicoperation.Remainder;
import slogo.command.math.basicoperation.Sum;
import slogo.command.math.comparator.Equals;
import slogo.command.math.comparator.Greater;
import slogo.command.math.comparator.GreaterEqual;
import slogo.command.math.comparator.Less;
import slogo.command.math.comparator.LessEqual;
import slogo.command.math.comparator.NotEquals;
import slogo.command.math.random.Random;
import slogo.command.math.random.RandomRange;
import slogo.command.value.GenericValue;

class BasicOperationTest {
  private List<Command> parameters;
  private Command parameterOne;
  private Command parameterTwo;
  private Command parameterThree;
  private Math command;

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    parameterOne = new GenericValue(5.0);
    parameterTwo = new GenericValue(2.0);
    parameterThree = new GenericValue(3.0);
  }

  @Test
  void testBasicOperationsHappy() {
    parameters.add(parameterOne);
    parameters.add(parameterTwo);

    try {
      command = new Difference(parameters);
      assertEquals(3.0, command.execute(null, null).returnVal());

      command = new Power(parameters);
      assertEquals(25.0, command.execute(null, null).returnVal());

      command = new Product(parameters);
      assertEquals(10.0, command.execute(null, null).returnVal());

      command = new Quotient(parameters);
      assertEquals(2.5, command.execute(null, null).returnVal());

      command = new Remainder(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new Sum(parameters);
      assertEquals(7.0, command.execute(null, null).returnVal());

      command = new RandomRange(parameters);
      Double random = command.execute(null, null).returnVal();
      if(!(random >= 2 && random < 5)) fail();

    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBasicOperationsSad() {
    assertThrows(WrongParameterNumberException.class, () -> new Difference(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Power(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Product(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Quotient(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Remainder(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Sum(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new RandomRange(parameters));

    parameters.add(parameterOne);
    assertThrows(WrongParameterNumberException.class, () -> new Difference(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Power(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Product(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Quotient(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Remainder(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Sum(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new RandomRange(parameters));

    parameters.add(parameterOne);
    parameters.add(parameterTwo);
    parameters.add(parameterThree);
    assertThrows(WrongParameterNumberException.class, () -> new Difference(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Power(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Product(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Quotient(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Remainder(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new Sum(parameters));
    assertThrows(WrongParameterNumberException.class, () -> new RandomRange(parameters));
  }
}