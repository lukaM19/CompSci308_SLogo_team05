package slogo.command.general;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.actorcommand.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.math.basicoperation.Product;
import slogo.command.math.basicoperation.Sum;
import slogo.command.value.GenericValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CommandSumListTest {
  private List<Command> parameters;
  private Command commandOne;
  private Command commandTwo;
  private CommandSumList commandList;

  @BeforeEach
  void setup() {
    parameters = new ArrayList<>();
    commandOne = new Sum(List.of(new GenericValue(3.0), new GenericValue(7.0)));
    commandOne.setImpliedParameters(Map.of(ActorCommand.SCALE_KEY, "1"));
    commandTwo = new Product(List.of(new GenericValue(5.0), new GenericValue(10.0)));
    commandList = new CommandSumList(parameters);
  }

  @Test
  void testCommandList() {
    try {
      assertEquals(0.0, commandList.execute(null, null).returnVal());

      parameters.add(commandOne);
      parameters.add(commandTwo);
      assertEquals(60.0, commandList.execute(null, null).returnVal());

      parameters.clear();
      parameters.add(commandTwo);
      parameters.add(commandOne);
      assertEquals(60.0, commandList.execute(null, null).returnVal());

    } catch (CommandException e) {
      e.printStackTrace();
      fail();
    }
  }
}