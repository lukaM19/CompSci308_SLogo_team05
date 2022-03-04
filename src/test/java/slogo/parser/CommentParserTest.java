package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.commandtest.good.TestCommandNoArgs;
import slogo.commandtest.good.TestCommandOneArg;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentParserTest extends AbstractParserTest {
    private List<String> validComments = List.of(
            "#asdfsffeger",
            "#test",
            "# comment",
            "# this is a comment",
            "# this is a comment\newkjnfwejnfkwenjk",
            "#",
            " # leading space"
    );

    private List<String> invalidComments = List.of(
            "testnoargs",
            "",
            "a",
            " words # comment"
    );

    @BeforeEach
    void setup () {
        setParser(new CommentParser());
    }

    @Test
    void testCanParse() {
        for(String s : validComments) {
            assertTrue(parser.canParse(new Scanner(s).next()));
        }
    }

    @Test
    void testCantParse() {
        for(String s : invalidComments) {
            assertFalse(parser.canParse(s.isBlank() ? s : new Scanner(s).next()));
        }
    }

    @Test
    void testParseComments() throws ParserException {
        for(String s : validComments) {
            verifier.assertCommandEmpty(s);
        }
    }
}
