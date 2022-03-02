package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;
import slogo.commandtest.good.TestCommandNoArgs;
import slogo.commandtest.good.TestCommandOneArg;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SlogoParserTest {
    private static final String REAL_COMMAND_PKG = "slogo.command.actorcommand.move";
    private static final String GOOD_COMMAND_PKG = "slogo.commandtest.good";
    private static final String BAD_COMMAND_PKG = "slogo.commandtest.bad";

    private CommandVerifier verifier;
    private SlogoParser parser;
    private CommandParserTest cmdParserTest;

    @BeforeEach
    void setup() {
        parser = new SlogoParser();
        verifier = new CommandVerifier(parser);
        cmdParserTest = new CommandParserTest();
        cmdParserTest.setParser(parser);
    }

    // Load some of the actual commands, not the testing set
    @Test
    void testLoadRealMoveCommands() {
        assertDoesNotThrow(() -> parser.loadCommands(REAL_COMMAND_PKG));
        assertTrue(parser.canParse("fd")); // From RelativeDistance
    }

    @Test
    void testLoadGoodCommands() {
        assertDoesNotThrow(() -> parser.loadCommands(GOOD_COMMAND_PKG));
        assertTrue(parser.canParse("testnoargs")); // From TestCommandNoArgs
        assertTrue(parser.canParse("testonearg")); // From TestCommandNoArgs
    }

    @Test
    void testLoadBadCommands() {
        assertThrows(ParserException.class, () -> parser.loadCommands(BAD_COMMAND_PKG));
        assertFalse(parser.canParse("badtest")); // From TestCommandBadConstructor
    }

    @Test
    void testParseLoneCommand() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        String oneArgStr = "TestOneArg 10";
        Command expected = new TestCommandOneArg(List.of(new GenericValue(10d)));

        cmdParserTest.testParseLoneCommand();
        verifier.assertCommandMatch(oneArgStr, expected);
        assertParseMatch(oneArgStr, expected);
    }

    @Test
    void testParseMultipleCommands() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        Command expectedResult = new CommandList(List.of(new TestCommandNoArgs(null), new TestCommandNoArgs(null)));
        List<String> testStrings = List.of(
                "TestNoArgs TestNoArgs",
                "TestNoArgs\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs\n",
                "\nTestNoArgs\n\nTestNoArgs\n"
        );

        for(String testString : testStrings) {
            assertParseMatch(testString, expectedResult);
        }
    }

    @Test
    void testParseOnlyFirstToken() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        Command expectedResult = new TestCommandNoArgs(null);
        List<String> testStrings = List.of(
                "TestNoArgs TestNoArgs",
                "TestNoArgs\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs",
                "TestNoArgs\n\nTestNoArgs\n"
        );

        for(String testString : testStrings) {
            verifier.assertCommandMatch(testString, expectedResult);
        }
    }

    void testParseCommandsWithComments() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        Command expectedResult = new CommandList(List.of(new TestCommandOneArg(List.of(new TestCommandOneArg(List.of(new TestCommandNoArgs(null)))))));
        List<String> testStrings = List.of(
                "TestOneArg\nTestOneArg\nTestNoArgs # test comment",
                "TestOneArg\nTestOneArg #test\nTestNoArgs # test comment",
                "TestOneArg\n#thisisacomment\nTestOneArg \n#test\nTestNoArgs # test comment"
        );

        for(String testString : testStrings) {
            assertParseMatch(testString, expectedResult);
        }
    }

    private void assertParseMatch(String commands, Command expectedResult) throws ParserException {
        assertTrue(verifier.verifyCommandStructure(parser.parse(commands), expectedResult));
    }

}
