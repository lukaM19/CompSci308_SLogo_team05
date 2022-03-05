package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandSumList;
import slogo.command.value.GenericValue;
import slogo.commandtest.good.TestCommandTwoArgs;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UnlimitedParameterParserTest extends AbstractParserTest {
    static final Command twenty = new GenericValue(20d);

    private List<String> validParams = List.of(
            "( TestOneArg 10 )",
            "( TestTwoArgs 10 10 20 20 )",
            "( TestOneArg 10 10 )",
            "( TestTwoArgs 10 ( TestTwoArgs\n 10 10 ) 20\n20 )",
            "( TestTwoArgs 20  10 20 10 )",
            "( TestNoArgs )",
            "( TestOneArg 10 # hi \n 10 )"
    );

    private List<Command> expectedLists = List.of(
            new CommandSumList(List.of(SlogoParserTest.oneArg)),
            new CommandSumList(List.of(new TestCommandTwoArgs(List.of(SlogoParserTest.ten, SlogoParserTest.ten)), new TestCommandTwoArgs(List.of(twenty, twenty)))),
            new CommandSumList(List.of(SlogoParserTest.oneArg, SlogoParserTest.oneArg)),
            new CommandSumList(List.of(new TestCommandTwoArgs(List.of(SlogoParserTest.ten, new CommandSumList(List.of(new TestCommandTwoArgs(List.of(SlogoParserTest.ten, SlogoParserTest.ten)))))), new TestCommandTwoArgs(List.of(twenty, twenty)))),
            new CommandSumList(List.of(new TestCommandTwoArgs(List.of(twenty, SlogoParserTest.ten)), new TestCommandTwoArgs(List.of(twenty, SlogoParserTest.ten)))),
            new CommandSumList(List.of(SlogoParserTest.noArg)),
            new CommandSumList(List.of(SlogoParserTest.oneArg, SlogoParserTest.oneArg))
    );

    private List<String> invalidParams = List.of(
            ")",
            "hi",
            "agh",
            "(hello",
            "34(",
            "[",
            "{"
    );

    @BeforeEach
    void setup () {
        setParser(new UnlimitedParameterParser(null));

        // Testing the test class I guess
        assert(validParams.size() == expectedLists.size());
    }

    @Test
    void testCanParse() {
        for(String s : validParams) {
            assertTrue(parser.canParse(new Scanner(s).next()));
        }
    }

    @Test
    void testCantParse() {
        for(String s : invalidParams) {
            assertFalse(parser.canParse(s.isBlank() ? s : new Scanner(s).next()));
        }
    }

    void testBadInput() {
        Scanner sc1 = new Scanner("( TestOneArg 10 10 )");
        assertThrows(ParserException.class, () -> parser.parseToken("lies", sc1));
        Scanner sc2 = new Scanner("( hello 10 10 )");
        assertThrows(ParserException.class, () -> parser.parseToken(sc2.next(), sc2));
        Scanner sc3 = new Scanner("( TestOneArg 10 10 )10 )");
        assertThrows(ParserException.class, () -> parser.parseToken(sc3.next(), sc3));
    }

    void testParseParams() throws ParserException {
        Iterator<String> stringIterator = validParams.iterator();
        Iterator<Command> expectedIterator = expectedLists.iterator();
        while(stringIterator.hasNext() && expectedIterator.hasNext()) {
            verifier.assertCommandMatch(stringIterator.next(), expectedIterator.next());
        }
    }
}
