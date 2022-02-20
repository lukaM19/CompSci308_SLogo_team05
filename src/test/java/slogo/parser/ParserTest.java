package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.general.ParameterGetter;
import slogo.command.general.good.TestCommandNoArgs;
import slogo.command.general.good.TestCommandOneArg;
import slogo.command.value.GenericValue;

import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private static final String GOOD_COMMAND_PKG = "slogo.command.general.good";
    private static final String BAD_COMMAND_PKG = "slogo.command.general.bad";
    private Parser parser;

    @BeforeEach
    void setup () {
        parser = new Parser();
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

    private boolean verifyCommandStructure(Command target, Command check) {
        if(target.getClass() != check.getClass() || target.getParametersSize() != check.getParametersSize()) {
            return false;
        }

        for(int i = 0; i < target.getParametersSize(); i++) {
            if(!verifyCommandStructure(ParameterGetter.getParameter(target, i), ParameterGetter.getParameter(check, i))) {
                return false;
            }
        }

        return true;
    }

}
