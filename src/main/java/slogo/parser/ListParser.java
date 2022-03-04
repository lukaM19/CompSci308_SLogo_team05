package slogo.parser;

import slogo.command.general.Command;
import slogo.command.general.CommandList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ListParser extends AbstractParser {
    private AbstractParser listElementParser;

    /**
     * Initializes this ListParser.
     * @param elementParser the parser to use for each element of this list
     */
    public ListParser(AbstractParser elementParser) {
        listElementParser = elementParser;
    }

    /**
     * Sets the parser to be used for parsing elements of the list
     * @param parser the parser to use for parsing elements of the list
     */
    public void setListElementParser(AbstractParser parser) {
        listElementParser = parser;
    }

    @Override
    public boolean canParse(String token) {
        return token.equals("[");
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        if(!token.equals("[")) {
            throw new IllegalArgumentException("Lists must always begin with a [");
        }
        List<Command> list = new ArrayList<>();
        token = sc.next();
        while(!token.equals("]")) {
            if(!listElementParser.canParse(token)) {
                throw newParserException("ParserTokenNotRecognized", token);
            }
            Optional<Command> item = listElementParser.parseToken(token, sc);
            item.ifPresent(list::add);
            token = sc.next();
        }

        return Optional.of(new CommandList(list));
    }
}
