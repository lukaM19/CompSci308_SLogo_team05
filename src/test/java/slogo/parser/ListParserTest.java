package slogo.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ListParserTest extends AbstractParserTest {
    static final Command emptyList = new CommandList(null);

    private List<String> validLists = List.of(
            "[ ]",
            "[ [ ] [ ] ]",
            "[ [ [ ] ] ]",
            "[ [ ] [ [ ]\n] ]",
            "[ ] hi"
    );

    private List<Command> expectedLists = List.of(
            emptyList,
            new CommandList(List.of(emptyList, emptyList)),
            new CommandList(List.of(new CommandList(List.of(emptyList)))),
            new CommandList(List.of(emptyList, new CommandList(List.of(emptyList)))),
            emptyList
    );

    private List<String> invalidLists = List.of(
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
        ListParser parser = new ListParser(null);
        parser.setListElementParser(parser);
        setParser(parser);

        // Testing the test class I guess
        assert(validLists.size() == expectedLists.size());
    }

    @Test
    void testCanParse() {
        for(String s : validLists) {
            assertTrue(parser.canParse(new Scanner(s).next()));
        }
    }

    @Test
    void testCantParse() {
        for(String s : invalidLists) {
            assertFalse(parser.canParse(s.isBlank() ? s : new Scanner(s).next()));
        }
    }

    @Test
    void testBadElements() {
        Scanner sc = new Scanner("[ [ ] hi ]");

        assertThrows(ParserException.class, () -> parser.parseToken(sc.next(), sc));
    }

    @Test
    void testParseLists() throws ParserException {
        Iterator<String> stringIterator = validLists.iterator();
        Iterator<Command> expectedIterator = expectedLists.iterator();
        while(stringIterator.hasNext() && expectedIterator.hasNext()) {
            verifier.assertCommandMatch(stringIterator.next(), expectedIterator.next());
        }
    }
}
