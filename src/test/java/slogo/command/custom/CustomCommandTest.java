package slogo.command.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.exception.CommandException;
import slogo.command.general.CommandList;
import slogo.command.value.Assigment;
import slogo.command.value.GenericValue;
import slogo.command.value.UserValue;
import slogo.parser.CommandParser;
import slogo.parser.CustomCommandParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CustomCommandTest {
    private CustomCommandParser parser;
    private Map<String, Double> userVars;

    @BeforeEach
    void setup() {
        parser = new CustomCommandParser(null, null);
        userVars = new HashMap<>();
        userVars.put("test", 10d);
    }

    @Test
    void testCreateCustomCommand() throws CommandException {
        CreateCustomCommand goodCommand = new CreateCustomCommand(parser, "test", new CommandList(null), new CommandList(null));

        assertEquals(goodCommand.execute(null, null).returnVal(), 1d);
        assertTrue(parser.canParse("test"));
    }

    @Test
    void testRunCustomCommand() throws CommandException {
        UserValue testVar = new UserValue("test");
        RunCustomCommand emptyCommand = new RunCustomCommand(List.of(new CommandList(List.of(new Assigment(List.of(testVar, new GenericValue(20d))))), new CommandList(List.of(testVar))));
        assertEquals(emptyCommand.execute(null, userVars).returnVal(), 20d);
        assertEquals(userVars.get("test"), 20d);
    }
}
