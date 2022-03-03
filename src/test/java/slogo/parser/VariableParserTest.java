package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.UserValue;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class VariableParserTest extends AbstractParserTest {
    static final Command testVariable = new UserValue("test");

    private List<String> validVars = List.of(
            ":test",
            ":hi",
            ":ekrjfbwjebfjhehfb",
            ":a",
            ":AAA",
            ":aAaA"
    );

    private List<String> invalidVars = List.of(
            "test",
            "test:",
            "_:test",
            ":10",
            ":test1",
            ":jkfnej_fjnek",
            "[ ]",
            ":[ ]",
            ":+",
            "10",
            "help",
            "-10",
            "] [",
            "][",
            "09987",
            "TestNoArgs",
            ".",
            "",
            "list",
            "opening square bracket"
    );

    @BeforeEach
    void setup () {
        setParser(new VariableParser());
    }

    @Test
    void testCanParse() {
        for(String s : validVars) {
            assertTrue(parser.canParse(new Scanner(s).next()));
        }
    }

    @Test
    void testCantParse() {
        for(String s : invalidVars) {
            assertFalse(parser.canParse(s.isBlank() ? s : new Scanner(s).next()));
        }
    }

    @Test
    void testParseVariables() throws ParserException {
        for(String var : validVars) {
            verifier.assertCommandMatch(var, new UserValue(var.substring(1)));
        }
    }
}
