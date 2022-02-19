package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    void setup () {
        parser = new Parser();
    }

    @Test
    void testLoadGoodCommands() {
        assertDoesNotThrow(() -> parser.loadCommands("slogo.command.test.good"));
        assertTrue(parser.hasCommand("testnoargs")); // From TestCommandNoArgs
        assertTrue(parser.hasCommand("testonearg")); // From TestCommandNoArgs
    }

    @Test
    void testLoadBadCommands() {
        assertThrows(ParserException.class, () -> parser.loadCommands("slogo.command.test.bad"));
        assertFalse(parser.hasCommand("badtest")); // From TestCommandBadConstructor
    }

}
