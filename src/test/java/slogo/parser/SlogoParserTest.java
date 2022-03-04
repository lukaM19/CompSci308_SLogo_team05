package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;
import slogo.commandtest.good.TestCommandNoArgs;
import slogo.commandtest.good.TestCommandOneArg;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SlogoParserTest {
    private static final String REAL_COMMAND_PKG = "slogo.command.actorcommand.move";
    private static final String GOOD_COMMAND_PKG = "slogo.commandtest.good";
    private static final String BAD_COMMAND_PKG = "slogo.commandtest.bad";

    private static final Command ten = new GenericValue(10d);
    private static final Command oneArg = new TestCommandOneArg(List.of(ten));
    private static final Command noArg = new TestCommandNoArgs(null);

    private CommandVerifier verifier;
    private SlogoParser parser;

    private Map<Class<? extends AbstractParserTest>, AbstractParserTest> parserTests = new HashMap<>() {{
        put(CommandParserTest.class, new CommandParserTest());
        put(CommentParserTest.class, new CommentParserTest());
        put(ConstantParserTest.class, new ConstantParserTest());
        put(ListParserTest.class, new ListParserTest());
        put(VariableParserTest.class, new VariableParserTest());
    }};

    @BeforeEach
    void setup() {
        parser = new SlogoParser();
        verifier = new CommandVerifier(parser);

        for(AbstractParserTest parserTest : parserTests.values()) {
            parserTest.setParser(parser);
        }
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
    void testCanParse() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        for(AbstractParserTest parserTest : parserTests.values()) {
            parserTest.testCanParse();
        }
    }

    @Test
    void testCantParse() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        assertFalse(parser.canParse(""));
        assertFalse(parser.canParse("a"));
        assertFalse(parser.canParse("dwqdqwd wqrqwd"));
        assertFalse(parser.canParse("dwqdwq 10"));
        assertFalse(parser.canParse("kejnwrkjnew TestNoArgs"));
        assertFalse(parser.canParse("hello# wfkwen"));
        assertFalse(parser.canParse("[TestNoArgs"));
        assertFalse(parser.canParse("10["));
        assertFalse(parser.canParse(":["));
        assertFalse(parser.canParse("[:"));
    }

    @Test
    void testParseLoneCommand() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        String oneArgStr = "TestOneArg 10";

        getTestParser(CommandParserTest.class).testParseLoneCommand();
        verifier.assertCommandMatch(oneArgStr, oneArg);
        assertParseMatch(oneArgStr, oneArg);
    }

    @Test
    void testParseMultipleCommands() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        Command expectedResult = new CommandList(List.of(noArg, noArg));
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

        getTestParser(CommandParserTest.class).testOnlyParseFirstCommand();
        verifier.assertCommandMatch("10\n20", ten);
        verifier.assertCommandMatch("TestOneArg 10 10", oneArg);
        verifier.assertCommandMatch("10 TestNoArgs", ten);
        verifier.assertCommandEmpty("# this is a test\n10");
        verifier.assertCommandEmpty("#comment\nTestNoArgs");
        verifier.assertCommandMatch("[ ] 10", ListParserTest.emptyList);
        verifier.assertCommandMatch(":test 10", VariableParserTest.testVariable);
    }

    @Test
    void testParseCommandsWithComments() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        Command expectedResult = new TestCommandOneArg(List.of(new TestCommandOneArg(List.of(noArg))));
        List<String> testStrings = List.of(
                "TestOneArg\nTestOneArg\nTestNoArgs # test comment",
                "TestOneArg\nTestOneArg #test\nTestNoArgs # test comment",
                "TestOneArg\n#thisisacomment\nTestOneArg \n#test\nTestNoArgs # test comment"
        );

        for(String testString : testStrings) {
            assertParseMatch(testString, expectedResult);
        }
    }

    @Test
    void testParseAssorted() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        assertParseMatch("10 TestOneArg 10", new CommandList(List.of(ten, oneArg)));
        assertParseMatch("# a comment\n10 TestOneArg 10", new CommandList(List.of(ten, oneArg)));
        assertParseMatch("TestNoArgs #comment\nTestOneArg 10 10", new CommandList(List.of(noArg, oneArg, ten)));
        assertParseMatch("TestNoArgs 10 #comment TestOneArg 10 10", new CommandList(List.of(noArg, ten)));
        assertParseMatch("TestNoArgs #comment\n10 TestOneArg TestOneArg 10\n\n", new CommandList(List.of(noArg, ten, new TestCommandOneArg(List.of(oneArg)))));
        assertParseMatch("[ TestNoArgs #comment\n10 TestOneArg TestOneArg 10\n\n]", new CommandList(List.of(noArg, ten, new TestCommandOneArg(List.of(oneArg)))));
        assertParseMatch("[ 10 ]", new CommandList(List.of(ten)));
        assertParseMatch("[ TestOneArg 10 ]", new CommandList(List.of(oneArg)));
        assertParseMatch("TestOneArg [ 10 10 10 ]", new TestCommandOneArg(List.of(new CommandList(List.of(ten, ten, ten)))));
        assertParseMatch("TestNoArgs #comment\n[ TestOneArg 10 ]  10", new CommandList(List.of(noArg, new CommandList(List.of(oneArg)), ten)));
        assertParseMatch("[ :test ]", new CommandList(List.of(VariableParserTest.testVariable)));
        assertParseMatch("[ :test :test ]", new CommandList(List.of(VariableParserTest.testVariable, VariableParserTest.testVariable)));
        assertParseMatch("TestOneArg :test 10", new CommandList(List.of(new TestCommandOneArg(List.of(VariableParserTest.testVariable)), ten)));
    }

    @Test
    void testParseComments() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        getTestParser(CommentParserTest.class).testParseComments();
    }

    @Test
    void testParseConstants() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        getTestParser(ConstantParserTest.class).testParseConstants();
    }

    @Test
    void testParseLists() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        getTestParser(ListParserTest.class).testParseLists();
    }

    @Test
    void testParseVariables() throws ParserException {
        parser.loadCommands(GOOD_COMMAND_PKG);

        getTestParser(VariableParserTest.class).testParseVariables();
    }

    private <T extends AbstractParserTest> T getTestParser(Class<T> clazz) {
        return (T)parserTests.get(clazz);
    }

    private void assertParseMatch(String commands, Command expectedResult) throws ParserException {
        verifier.verifyCommandStructure(parser.parse(commands), expectedResult, true);
    }

}
