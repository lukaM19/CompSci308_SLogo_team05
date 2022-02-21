package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.general.ParameterGetter;
import slogo.command.test.good.TestCommandNoArgs;
import slogo.command.test.good.TestCommandOneArg;
import slogo.command.value.GenericValue;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private static final String GOOD_COMMAND_PKG = "slogo.command.test.good";
    private static final String BAD_COMMAND_PKG = "slogo.command.test.bad";
    private Parser parser;

    @BeforeEach
    void setup () {
        parser = new Parser();
    }

    // Load some of the actual commands, not the testing set
    @Test
    void testLoadRealMoveCommands() {
        assertDoesNotThrow(() -> parser.loadCommands("slogo.command.actorcommand.move"));
        assertTrue(parser.hasCommand("fd")); // From RelativeDistance
    }

    @Test
    void testLoadGoodCommands() {
        assertDoesNotThrow(() -> parser.loadCommands(GOOD_COMMAND_PKG));
        assertTrue(parser.hasCommand("testnoargs")); // From TestCommandNoArgs
        assertTrue(parser.hasCommand("testonearg")); // From TestCommandNoArgs
    }

    @Test
    void testLoadBadCommands() {
        assertThrows(ParserException.class, () -> parser.loadCommands(BAD_COMMAND_PKG));
        assertFalse(parser.hasCommand("badtest")); // From TestCommandBadConstructor
    }

    @Test
    void testParseLoneCommand() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);
        assertDoesNotThrow(() -> parser.parse("TestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestNoArgs"), new CommandList(List.of(new TestCommandNoArgs(null))));
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        assertDoesNotThrow(() -> parser.parse("TestOneArg 10"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg 10"), new CommandList(List.of(new TestCommandOneArg(List.of(new GenericValue(10d))))));
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    @Test
    void testParseMultipleCommands() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);
        Command doubleNoArgs = new CommandList(List.of(new TestCommandNoArgs(null), new TestCommandNoArgs(null)));
        assertDoesNotThrow(() -> parser.parse("TestNoArgs TestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestNoArgs TestNoArgs"), doubleNoArgs);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        assertDoesNotThrow(() -> parser.parse("TestNoArgs\nTestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestNoArgs\nTestNoArgs"), doubleNoArgs);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        assertDoesNotThrow(() -> parser.parse("TestNoArgs\n\nTestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestNoArgs\nTestNoArgs"), doubleNoArgs);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    @Test
    void testCommandsAsParameters() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);
        Command expectedCommand1 = new CommandList(List.of(new TestCommandOneArg(List.of(new TestCommandNoArgs(null)))));
        assertDoesNotThrow(() -> parser.parse("TestOneArg\nTestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg\nTestNoArgs"), expectedCommand1);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        Command expectedCommand2 = new CommandList(List.of(new TestCommandOneArg(List.of(new TestCommandOneArg(List.of(new TestCommandNoArgs(null)))))));
        assertDoesNotThrow(() -> parser.parse("TestOneArg\nTestOneArg TestNoArgs"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg\nTestOneArg TestNoArgs"), expectedCommand2);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    @Test
    void testCommandsWithComments() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);
        Command expectedCommand = new CommandList(List.of(new TestCommandOneArg(List.of(new TestCommandOneArg(List.of(new TestCommandNoArgs(null)))))));
        assertDoesNotThrow(() -> parser.parse("TestOneArg\nTestOneArg\nTestNoArgs # test comment"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg\nTestOneArg\nTestNoArgs # test comment"), expectedCommand);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        assertDoesNotThrow(() -> parser.parse("TestOneArg\nTestOneArg #test\nTestNoArgs # test comment"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg\nTestOneArg #test\nTestNoArgs # test comment"), expectedCommand);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
        assertDoesNotThrow(() -> parser.parse("TestOneArg\n#thisisacomment\nTestOneArg #test\nTestNoArgs # test comment"));
        assertTrue(() -> {
            try {
                return verifyCommandStructure(parser.parse("TestOneArg\n#thisisacomment\nTestOneArg #test\nTestNoArgs # test comment"), expectedCommand);
            } catch (ParserException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    private boolean verifyCommandStructure(Command target, Command check) {
        if(target.getClass() != check.getClass() || ParameterGetter.getParametersSize(target) != ParameterGetter.getParametersSize(check)) {
            return false;
        }

        for(int i = 0; i < ParameterGetter.getParametersSize(target); i++) {
            if(!verifyCommandStructure(ParameterGetter.getParameter(target, i), ParameterGetter.getParameter(check, i))) {
                return false;
            }
        }

        return true;
    }

}
