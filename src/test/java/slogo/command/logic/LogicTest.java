package slogo.command.logic;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.general.Command.DEFAULT_VALUE;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.operator.And;
import slogo.command.logic.operator.Not;
import slogo.command.logic.operator.Or;
import slogo.command.value.GenericValue;

class LogicTest {
  private List<Command> parameters;
  private GenericValue trueCommand;
  private GenericValue falseCommand;

  @BeforeEach
  void setUp() {
    parameters = new ArrayList<>();
    trueCommand = new GenericValue(10.0);
    falseCommand = new GenericValue(DEFAULT_VALUE);
  }

  @Test
  void testOneInputLogicHappy() throws CommandException {
    parameters.add(trueCommand);
    Logic notCommand = new Not(parameters);
    assertEquals(0.0, notCommand.execute(null, null).returnVal());

    parameters.clear();
    parameters.add(falseCommand);
    assertEquals(1.0, notCommand.execute(null, null).returnVal());
  }

  @Test
  void testOneInputLogicSad() throws CommandException {
    Logic notCommand = new Not(parameters);
    assertThrows(WrongParameterNumberException.class, () -> notCommand.execute(null, null));

    parameters.add(falseCommand);
    parameters.add(trueCommand);
    assertThrows(WrongParameterNumberException.class, () -> notCommand.execute(null, null));
  }

  @Test
  void testTwoInputLogicSad() throws CommandException {
    parameters.add(falseCommand);
    Logic andCommand = new And(parameters);
    Logic orCommand = new Or(parameters);
    assertThrows(WrongParameterNumberException.class, () -> andCommand.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> orCommand.execute(null, null));

    parameters.add(trueCommand);
    parameters.add(falseCommand);
    assertThrows(WrongParameterNumberException.class, () -> andCommand.execute(null, null));
    assertThrows(WrongParameterNumberException.class, () -> orCommand.execute(null, null));
  }

  @Test
  void testTwoInputLogicHappy() throws CommandException {
    Logic andCommand = new And(parameters);
    Logic orCommand = new Or(parameters);

    parameters.add(trueCommand);
    parameters.add(trueCommand);

    assertEquals(1.0, andCommand.execute(null, null).returnVal());
    assertEquals(1.0, orCommand.execute(null, null).returnVal());

    parameters.clear();
    parameters.add(trueCommand);
    parameters.add(falseCommand);

    assertEquals(0.0, andCommand.execute(null, null).returnVal());
    assertEquals(1.0, orCommand.execute(null, null).returnVal());

    parameters.clear();
    parameters.add(falseCommand);
    parameters.add(falseCommand);

    assertEquals(0.0, andCommand.execute(null, null).returnVal());
    assertEquals(0.0, orCommand.execute(null, null).returnVal());
  }
}