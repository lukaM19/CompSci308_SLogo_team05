package slogo.parser;

import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.AllArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.custom.CreateCustomCommand;
import slogo.command.custom.RunCustomCommand;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.Assigment;
import slogo.command.value.UserValue;
import slogo.commandtest.good.TestCommandNoArgs;
import slogo.commandtest.good.TestCommandOneArg;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CustomCommandParserTest extends AbstractParserTest {
    private final UserValue arg = new UserValue("arg");

    @BeforeEach
    void setup () {
        CustomCommandParser cmdParser = new CustomCommandParser(null, null);
        // Can only contain variables in body or args
        cmdParser.setParsers(new ListParser(new VariableParser()), new CommandParser(null));
        cmdParser.registerCommand("test", new CommandList(List.of(arg)), new CommandList(List.of(SlogoParserTest.oneArg)));
        setParser(cmdParser);
    }

    @Test
    void testCanParse() {
        assertTrue(parser.canParse("to"));
        assertTrue(parser.canParse("To"));
        assertTrue(parser.canParse("tEST"));
        assertTrue(parser.canParse("test"));

        assertFalse(parser.canParse("unregistered"));
        assertFalse(parser.canParse("fd"));
        assertFalse(parser.canParse("toast"));
        assertFalse(parser.canParse("["));
    }

    @Test
    void testParseRegisterCustomCommand() throws ParserException {
        verifier.assertCommandMatch("to hi [ :hello ] [ :hello ]", new CreateCustomCommand(null, null, null, null));
    }

    @Test
    void testBadArguments() {
        assertThrows(ParserException.class, () -> parser.parseToken("to", new Scanner("hi :hello [ ]")));
        assertThrows(ParserException.class, () -> parser.parseToken("to", new Scanner("hi [ ] :hello")));
    }

    @Test
    void testParseRunCustomCommands() throws ParserException {
        verifier.assertCommandMatch("test [ ]", new RunCustomCommand(List.of(new CommandList(List.of(new Assigment(List.of(arg, new CommandList(null))))), new CommandList(List.of(SlogoParserTest.oneArg)))));
    }

}
