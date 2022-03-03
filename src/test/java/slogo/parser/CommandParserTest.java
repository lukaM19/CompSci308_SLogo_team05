package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.general.ParameterGetter;
import slogo.commandtest.good.TestCommandNoArgs;
import slogo.commandtest.good.TestCommandOneArg;
import slogo.command.value.GenericValue;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest extends AbstractParserTest {

    @BeforeEach
    void setup () {
        CommandParser cmdParser = new CommandParser(null);
        // The only arguments it can have are other commands
        cmdParser.setArgumentParser(cmdParser);
        cmdParser.registerCommand("testnoargs", TestCommandNoArgs.class, 0);
        cmdParser.registerCommand("testonearg", TestCommandOneArg.class, 1);
        setParser(cmdParser);
    }

    @Test
    void testCanParse() {
        assertTrue(parser.canParse("testnoargs"));
        assertTrue(parser.canParse("testonearg"));
        assertTrue(parser.canParse("TESTNOARGS"));
        assertTrue(parser.canParse("TeStNoArGs"));

        assertFalse(parser.canParse("testnoargss"));
        assertFalse(parser.canParse("ttestnoargs"));
        assertFalse(parser.canParse(""));
        assertFalse(parser.canParse("a"));
        assertFalse(parser.canParse("nonexistentcommand"));
        assertFalse(parser.canParse("testniargs"));
    }

    @Test
    void testParseLoneCommand() throws ParserException {
        verifier.assertCommandMatch("TestNoArgs", new TestCommandNoArgs(null));
    }

    @Test
    void testBadArguments() {
        Scanner sc = new Scanner("testonearg kjdwenfkjewbbj");

        assertThrows(ParserException.class, () -> parser.parseToken(sc.next(), sc));
    }

    @Test
    void testOnlyParseFirstCommand() throws ParserException {
        Command expectedResult = new TestCommandNoArgs(null);
        List<String> testStrings = List.of(
                "TestNoArgs TestNoArgs",
                "TestNoArgs\tTestNoArgs",
                "TestNoArgs\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs\n",
                "TestNoArgs 10",
                "TestNoArgs\n10"
        );

        for(String testString : testStrings) {
            verifier.assertCommandMatch(testString, expectedResult);
        }
    }

    @Test
    void testCommandsAsParameters() throws ParserException {
        verifier.assertCommandMatch("TestOneArg\nTestNoArgs", new TestCommandOneArg(List.of(new TestCommandNoArgs(null))));
        verifier.assertCommandMatch("TestOneArg\nTestOneArg TestNoArgs", new TestCommandOneArg(List.of(new TestCommandOneArg(List.of(new TestCommandNoArgs(null))))));
    }

}
