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
    impliedParameters.put(VAR_NAME_KEY, impliedParamKey);
    impliedParameters.put(VAR_VALUE_KEY, impliedParamValue);

    Assigment command = new Assigment(null);
    command.setImpliedParameters(impliedParameters);

    assertEquals(10.0d, command.execute(null, userVars).returnVal());
    if(userVars.get(impliedParamKey) != 10.0) fail();
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
    assertThrows(ParameterNotFoundException.class, () -> genericValue.execute(null, null).returnVal());
    assertThrows(ParameterNotFoundException.class, () -> userValue.execute(null, null));
  }

  @Test
  void testAssignmentSad() {
    Assigment command = new Assigment(null);
    assertThrows(ImpliedParametersNotSetException.class, () -> command.setUpExecution(null, userVars));

    command.setImpliedParameters(impliedParameters);
    impliedParameters.put(VAR_NAME_KEY, impliedParamKey);
    assertThrows(ImpliedParameterNotFoundException.class, () -> command.setUpExecution(null, userVars));

    impliedParameters.put(VAR_VALUE_KEY, VAR_NAME_KEY);
    assertThrows(
        WrongImpliedParameterTypeException.class, () -> command.setUpExecution(null, userVars));
  }

}