package slogo.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import slogo.command.math.random.RandomRange;
import slogo.command.value.GenericValue;

class BasicOperationTest {
  private List<Command> parameters;
  private Map<String, String> impliedParameters;

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

    impliedParameters = new HashMap<>();
    impliedParameters.put(SCALE_KEY, "1");
  }

  @Test
  void testBasicOperationsHappy() {
    parameters.add(parameterOne);
    parameters.add(parameterTwo);

    try {
      command = new Power(parameters);
      assertEquals(25.0, command.execute(null, null).returnVal());

      command = new Product(parameters);
      assertEquals(10.0, command.execute(null, null).returnVal());

      command = new Quotient(parameters);
      assertEquals(2.5, command.execute(null, null).returnVal());

      command = new Remainder(parameters);
      assertEquals(1.0, command.execute(null, null).returnVal());

      command = new Sum(parameters);
      command.setImpliedParameters(impliedParameters);
      assertEquals(7.0, command.execute(null, null).returnVal());

      impliedParameters.put(SCALE_KEY, "-1");
      assertEquals(3.0, command.execute(null, null).returnVal());

      command = new RandomRange(parameters);
      Double random = command.execute(null, null).returnVal();
      if(!(random >= 2 && random < 5)) fail();

    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBasicOperationsSad() {
    Difference difference = new Difference(parameters);
    Power power = new Power(parameters);
    Product product = new Product(parameters);
    Quotient quotient = new Quotient(parameters);
    Remainder remainder = new Remainder(parameters);
    Sum sum = new Sum(parameters);
    RandomRange randomRange = new RandomRange(parameters);

    assertThrows(WrongParameterNumberException.class, () -> difference.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> power.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> product.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> quotient.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> remainder.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> sum.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> randomRange.execute(null, null));

    parameters.add(parameterOne);
    assertThrows(WrongParameterNumberException.class, () -> difference.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> power.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> product.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> quotient.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> remainder.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> sum.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> randomRange.execute(null, null));

    parameters.add(parameterOne);
    parameters.add(parameterTwo);
    parameters.add(parameterThree);
    assertThrows(WrongParameterNumberException.class, () -> difference.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> power.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> product.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> quotient.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> remainder.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> sum.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> randomRange.execute(null, null));
  }
}