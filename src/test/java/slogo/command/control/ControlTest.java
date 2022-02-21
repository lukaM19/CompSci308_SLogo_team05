package slogo.command.control;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.math.basicoperation.Product;
import slogo.command.value.GenericValue;
import slogo.command.value.UserValue;

class ControlTest {
  private List<Command> parameters;
  private GenericValue trueCommand;
  private GenericValue falseCommand;
  private CommandList bodyOne;
  private CommandList bodyTwo;

  private Map<String, Double> userVars;
  private String counterName;
  private GenericValue startingIndex;
  private GenericValue limit;
  private GenericValue increment;
  private CommandList forLoopBody;

  @BeforeEach
  void setUp() {
    parameters = new ArrayList<>();
    trueCommand = new GenericValue(10.0);
    falseCommand = new GenericValue(0.0);
    bodyOne = new CommandList(List.of(new GenericValue(1.0), new GenericValue(2.0)));
    bodyTwo = new CommandList(List.of(new GenericValue(2.0), new GenericValue(4.0), new GenericValue(3.0)));

    userVars = new HashMap<>();
    counterName = "i";
    startingIndex = new GenericValue(0.0);
    increment = new GenericValue(1.0);
    limit = new GenericValue(10.0);
    forLoopBody = new CommandList(List.of(new Product(List.of(new UserValue(counterName), new UserValue(counterName)))));
  }

  @Test
  void forLoopHappy() throws CommandException {
      ForLoop command = new ForLoop(parameters);
      command.setImpliedParameters(Map.of(VAR_NAME_KEY, counterName));
      parameters.add(startingIndex);
      parameters.add(limit);
      parameters.add(increment);
      parameters.add(forLoopBody);
      assertEquals(81.0, command.execute(null, userVars).returnVal());
      assertEquals(9.0, userVars.get(counterName));
  }

  @Test
  void forLoopSad() throws CommandException {
    ForLoop command = new ForLoop(parameters);

    parameters.add(startingIndex);
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, userVars));

    for(int i=0; i<5; i++) {
      parameters.add(startingIndex);
    }
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, userVars));

    parameters.clear();
    parameters.add(startingIndex);
    parameters.add(limit);
    parameters.add(increment);
    parameters.add(forLoopBody);
    assertThrows(ImpliedParametersNotSetException.class, () -> command.execute(null, userVars));

    command.setImpliedParameters(Map.of(VAR_VALUE_KEY, counterName));
    assertThrows(ImpliedParameterNotFoundException.class, () -> command.execute(null, userVars));

    command.setImpliedParameters(Map.of(VAR_NAME_KEY, counterName));
    assertThrows(UserVarMapNotFoundException.class, () -> command.execute(null, null));
  }

  @Test
  void testConditionalHappy() throws CommandException {
    Conditional command = new Conditional(parameters);

    parameters.add(trueCommand);
    parameters.add(bodyOne);
    assertEquals(2.0, command.execute(null, null).returnVal());

    parameters.clear();
    parameters.add(falseCommand);
    parameters.add(bodyOne);
    assertEquals(0.0, command.execute(null, null).returnVal());

    parameters.clear();
    parameters.add(falseCommand);
    parameters.add(bodyOne);
    parameters.add(bodyTwo);
    assertEquals(3.0, command.execute(null, null).returnVal());
  }

  @Test
  void testConditionalSad() throws CommandException {
    Conditional command = new Conditional(parameters);
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, null));

    parameters.add(falseCommand);
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, null));

    parameters.add(trueCommand);
    parameters.add(bodyOne);
    parameters.add(bodyTwo);
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, null));
  }
}