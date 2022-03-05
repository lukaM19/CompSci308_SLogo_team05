package slogo.command.value;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.general.Command.DEFAULT_VALUE;
import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.command.general.Command.VAR_VALUE_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.AllArguments.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.ParameterNotFoundException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterNotFoundException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParametersNotSetException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Math;

class ValueTest {
  private List<Command> parameters;
  private Map<String, String> impliedParameters;
  private Map<String, Double> userVars;

  private String key = "test";
  private Double value = 5.0;
  private String impliedParamKey = "implied";
  private String impliedParamValue = "10.0";

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    userVars = new HashMap<>();
    userVars.put(key, value);
    impliedParameters = new HashMap<>();
  }

  @Test
  void testAssignmentHappy() throws CommandException {
    Assigment command = new Assigment(List.of(new UserValue(key), new GenericValue(value)));

    assertEquals(value, command.execute(null, userVars).returnVal());
    assertTrue(userVars.containsKey(key));
    assertEquals(userVars.get(key), value);
  }

  @Test
  void testValuesHappy() throws CommandException {
    GenericValue genericValue = new GenericValue(value);
    UserValue userValue = new UserValue(key);

    assertEquals(value, genericValue.execute(null, null).returnVal());
    assertEquals(value, userValue.execute(null, userVars).returnVal());

    userValue = new UserValue(key+1);
    assertEquals(DEFAULT_VALUE, userValue.execute(null, userVars).returnVal());
  }

  @Test
  void testValuesSad() {
    GenericValue genericValue = new GenericValue(null);
    UserValue userValue = new UserValue(key);
    assertThrows(ParameterNotFoundException.class, () -> genericValue.execute(null, null));
    assertThrows(UserVarMapNotFoundException.class, () -> userValue.execute(null, null));

  }

  @Test
  void testAssignmentSad() {
    Assigment command = new Assigment(null);
    assertThrows(WrongParameterNumberException.class, () -> command.execute(null, userVars));

    final Assigment command2 = new Assigment(List.of(command, command));
    assertThrows(WrongParameterTypeException.class, () -> command2.execute(null, userVars));

    final Assigment command3 = new Assigment(List.of(new UserValue(key), new GenericValue(value)));
    assertThrows(UserVarMapNotFoundException.class, () -> command3.execute(null ,null));
  }

}