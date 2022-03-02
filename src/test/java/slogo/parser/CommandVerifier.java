package slogo.parser;

import slogo.command.general.Command;
import slogo.command.general.ParameterGetter;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandVerifier {
    private AbstractParser parser;

    CommandVerifier(AbstractParser parser) {
        this.parser = parser;
    }

    void assertCommandMatch(String token, Command expectedResult) throws ParserException {
        Scanner sc = new Scanner(token);
        Optional<Command> commandResult = parser.parseToken(sc.next(), sc);
        assertTrue(commandResult.isPresent());
        assertTrue(verifyCommandStructure(commandResult.get(), expectedResult));
    }

    void assertCommandEmpty(String token) throws ParserException {
        Scanner sc = new Scanner(token);
        Optional<Command> commandResult = parser.parseToken(token.isBlank() ? token : sc.next(), sc);
        assertTrue(commandResult.isEmpty());
    }

    boolean verifyCommandStructure(Command target, Command check) {
        if(target.getClass() != check.getClass() || ParameterGetter.getParametersSize(target) != ParameterGetter.getParametersSize(check)) {
            System.out.println("Expected class: " + check.getClass() + "\nActual class: " + target.getClass());
            return false;
        }

        for(int i = 0; i < ParameterGetter.getParametersSize(target); i++) {
            if(!verifyCommandStructure(ParameterGetter.getParameter(target, i), ParameterGetter.getParameter(check, i))) {
                return false;
            }
        }

        return true;
    }
}
