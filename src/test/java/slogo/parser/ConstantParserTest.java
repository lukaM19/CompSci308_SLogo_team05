package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.value.GenericValue;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstantParserTest extends AbstractParserTest {
    private List<String> validConstants = List.of(
            "1",
            "0",
            "0.1",
            "-0.5",
            "-1.6",
            "1.0",
            "13485783475847322398423982.219928482348723948239",
            "1.",
            "0.",
            "-0"
    );

    private List<String> invalidConstants = List.of(
            ".1",
            "-.5",
            "+1",
            "+0",
            "a12312432",
            "12321f",
            "1+1",
            ".",
            "",
            "hi",
            "#1",
            "1^1",
            "1e1",
            "1e+1",
            "two"
    );

    @BeforeEach
    void setup () {
        setParser(new ConstantParser());
    }

    @Test
    void testCanParse() {
        for(String s : validConstants) {
            assertTrue(parser.canParse(new Scanner(s).next()));
        }
    }

    @Test
    void testCantParse() {
        for(String s : invalidConstants) {
            assertFalse(parser.canParse(s.isBlank() ? s : new Scanner(s).next()));
        }
    }

    @Test
    void testParseConstants() throws ParserException {
        for(String test : validConstants) {
            // Use strict to check actual constant values
            verifier.assertCommandMatch(test, new GenericValue(Double.parseDouble(test)), true);
        }
    }
}
