package slogo.parser;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.general.ParameterGetter;
import slogo.command.value.GenericValue;
import slogo.command.value.UserValue;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CommandVerifier {
    private AbstractParser parser;

    CommandVerifier(AbstractParser parser) {
        this.parser = parser;
    }

    void assertCommandMatch(String token, Command expectedResult) throws ParserException {
        assertCommandMatch(token, expectedResult, false);
    }

    void assertCommandMatch(String token, Command expectedResult, boolean strict) throws ParserException {
        Scanner sc = new Scanner(token);
        Optional<Command> commandResult = parser.parseToken(sc.next(), sc);
        assertTrue(commandResult.isPresent());
        verifyCommandStructure(commandResult.get(), expectedResult, strict);
    }

    void assertCommandEmpty(String token) throws ParserException {
        Scanner sc = new Scanner(token);
        Optional<Command> commandResult = parser.parseToken(token.isBlank() ? token : sc.next(), sc);
        assertTrue(commandResult.isEmpty());
    }

    void verifyCommandStructure(Command target, Command check) {
        verifyCommandStructure(target, check, false);
    }

    // Strict checks GenericValue values and UserValue keys
    void verifyCommandStructure(Command target, Command check, boolean strict) {
        assertEquals(check.getClass(), target.getClass());
        assertEquals(check.getParametersSize(), target.getParametersSize());

        if(target instanceof GenericValue) {
            assertDoesNotThrow(() -> {
                assertEquals(check.execute(null, null).returnVal(), target.execute(null, null).returnVal());
            });
        } else if(target instanceof UserValue val) {
            assertEquals(val.getVarName(), ((UserValue) check).getVarName());
        }

        for(int i = 0; i < target.getParametersSize(); i++) {

            verifyCommandStructure(ParameterGetter.getParameter(check, i), ParameterGetter.getParameter(target, i));
        }
    }
}
