package slogo.parser;

import slogo.command.general.Command;
import slogo.command.general.CommandList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ListParser extends AbstractParser {
    private final AbstractParser listElementParser;

    /**
     * Initializes this ListParser.
     * @param elementParser the parser to use for each element of this list
     */
    public ListParser(AbstractParser elementParser) {
        listElementParser = elementParser;
    }

    @Override
    public boolean canParse(String token) {
        return token.equals("[");
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        List<Command> list = new ArrayList<Command>();
        token = sc.next();
        while(!token.equals("]")) {
            Optional<Command> item = parseToken(token, sc);
            item.ifPresent(list::add);
            token = sc.next();
        }

        return Optional.of(new CommandList(list));
    }
}
