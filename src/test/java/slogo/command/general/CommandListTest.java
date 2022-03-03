package slogo.command.general;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.math.basicoperation.Product;
import slogo.command.math.basicoperation.Sum;
import slogo.command.value.GenericValue;

class CommandListTest {
  private List<Command> parameters;
  private Command commandOne;
  private Command commandTwo;
  private CommandList commandList;

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    commandOne = new Sum(List.of(new GenericValue(3.0), new GenericValue(7.0)));
    commandOne.setImpliedParameters(Map.of(SCALE_KEY, "1"));
    commandTwo = new Product(List.of(new GenericValue(5.0), new GenericValue(10.0)));
    commandList = new CommandList(parameters);
  }

  @Test
  void testCommandList() {
    try {
      assertEquals(0.0, commandList.execute(null, null).returnVal());

      parameters.add(commandOne);
      parameters.add(commandTwo);
      assertEquals(50.0, commandList.execute(null, null).returnVal());

      parameters.clear();
      parameters.add(commandTwo);
      parameters.add(commandOne);
      assertEquals(10.0, commandList.execute(null, null).returnVal());

    } catch (CommandException e) {
      e.printStackTrace();
    }
  }
}