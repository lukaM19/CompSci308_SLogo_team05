package slogo.command.interpreter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.World;
import slogo.model.WorldTest;

public class Interpreter {

  public static final int DEFAULT_PARAM_NUMBER = 1;

  public static final HashMap<String, String> commandToMethodMap = new HashMap<>() {{
    put("sine", "slogo.command.math.trig.Sine");
    put("sin", "slogo.command.math.trig.Sine");
    put("sum", "slogo.command.math.basicoperation.Sum");
    put("product", "slogo.command.math.basicoperation.Product");
    put("difference", "slogo.command.math.basicoperation.Product");
    put("less", "slogo.command.math.comparator.Less");
  }};

  public static final HashMap<String, Integer> numParametersMap = new HashMap<>() {{
    put("slogo.command.math.basicoperation.Sum", 2);
    put("slogo.command.math.basicoperation.Difference", 2);
    put("slogo.command.math.basicoperation.Product", 2);
    put("slogo.command.math.comparator.Less", 2);
  }};

  public static void main(String[] args) {
    String message = "sum 10 sum 10 sum 10 sum 20 20";
    parseCommand(message);
  }

  public static void parseCommand(String message) {
    String[] split = message.split(" ");
    Stack<ParameterNumberChecker> commandNames = new Stack<>();
    Stack<Command> values = new Stack<>();

    for(String term: split) {
      try {
        values.push(new GenericValue(Double.parseDouble(term)));
        ParameterNumberChecker currentPNC = commandNames.peek();
        currentPNC.decrementAPN();

        if(currentPNC.parameterNumberReached()) {
          ArrayList<Command> parameters = new ArrayList<>();
          commandNames.pop();
          for(int i=0; i< currentPNC.getTotalParameterNumber(); i++) {
            parameters.add(values.pop());
          }
          values.push(getCommandFromString(currentPNC.getCommandName(), parameters));
        }
      } catch (NumberFormatException e) {
        String commandName = commandToMethodMap.get(term.toLowerCase());
        commandNames.push(new ParameterNumberChecker(commandName,  numParametersMap.getOrDefault(commandName, DEFAULT_PARAM_NUMBER)));
      }
      if (commandNames.isEmpty()) {
        System.out.println(values.pop().execute());
      }
    }
    while(!commandNames.isEmpty()) {
      ParameterNumberChecker currentPNC = commandNames.pop();
      ArrayList<Command> parameters = new ArrayList<>();
      for(int i=0; i< currentPNC.getTotalParameterNumber(); i++) {
        parameters.add(values.pop());
      }
      values.push(getCommandFromString(currentPNC.getCommandName(), parameters));
    }
    System.out.println(values.pop().execute());
  }

  private static Command getCommandFromString(String commandName, List<Command> parameters) {
    try {
      Class<?> commandClass = Class.forName(commandName);
      Constructor<?> ctor = commandClass.getDeclaredConstructor(World.class, List.class, Map.class);
      return (Command) ctor.newInstance(new WorldTest(), parameters, new HashMap<String, Object>());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

}
