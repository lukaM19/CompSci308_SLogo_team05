package slogo.parser;

import slogo.command.general.Command;
import slogo.command.general.CommandSumList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UnlimitedParameterParser extends AbstractParser {
    private CommandParser commandParser;

    /**
     * Initializes this UnlimitedParameterParser.
     * @param commandParser the parser to use for the commands involved
     */
    public UnlimitedParameterParser(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    /**
     * Sets the parser to be used for parsing the commands inside this
     * @param commandParser the parser to use for parsing commands
     */
    public void setCommandParser(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public boolean canParse(String token) {
        return token.equals("(");
    }

    @Override
    public Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        if(!token.equals("(")) {
            throw new IllegalArgumentException("Unlimited parameter expressions must always begin with a (");
        }
        List<Command> list = new ArrayList<>();
        String keyword = sc.next();
        if(!commandParser.canParse(keyword)) {
            throw newParserException("ParserTokenNotRecognized", keyword);
        }
        // Go until the next token is the closing parentheses we want
        // Always parse at least one
        do {
            Optional<Command> item = commandParser.parseToken(keyword, sc);
            item.ifPresent(list::add);
        } while((!sc.hasNext("\\)")));

        sc.next();

        return Optional.of(new CommandSumList(list));
    }
}
