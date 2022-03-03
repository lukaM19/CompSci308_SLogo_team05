package slogo.parser;

import org.reflections.Configuration;
import org.reflections.Reflections;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.parser.annotations.SlogoCommand;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Optional;

public class SlogoParser extends AbstractParser {
    private final CommandParser cmdParser = new CommandParser(this);
    private final Collection<AbstractParser> tokenParsers = new ArrayList<>(List.of(
        cmdParser,
        new ListParser(this),
        new ConstantParser(),
        new VariableParser(),
        new CommentParser()
    ));

    @Override
    public boolean canParse(String token) {
        return tokenParsers.stream().anyMatch(parser -> parser.canParse(token));
    }

    /**
     * Loads all commands in the package specified
     * @param cmdPackage the package to load commands from
     * @throws ParserException if any error occurs while loading commands
     */
    public void loadCommands(String cmdPackage) throws ParserException {
        loadCommands(new Reflections(cmdPackage));
    }

    /**
     * Loads all commands in the packages specified by the provided Configuration
     * @param config specifies where to load commands from
     * @throws ParserException if any error occurs while loading commands
     */
    public void loadCommands(Configuration config) throws ParserException {
        loadCommands(new Reflections(config));
    }

    private void loadCommands(Reflections reflections) throws ParserException {
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(SlogoCommand.class);
        for(Class<?> commandClass : commandClasses) {
            if(!Command.class.isAssignableFrom(commandClass)) {
                throw newParserException("ParserClassNotExtendingCommand");
            }
            // Must have constructor taking list of argument commands
            try {
                commandClass.getDeclaredConstructor(List.class);
            } catch(Exception ex) {
                throw newParserException("ParserCommandNoValidConstructor", ex, commandClass.getName());
            }

            SlogoCommand cmdAnnotation = commandClass.getAnnotation(SlogoCommand.class);
            for(String keywordID : cmdAnnotation.keywords()) {
                cmdParser.registerCommand(keywordID, commandClass.asSubclass(Command.class), cmdAnnotation.arguments());
            }
        }
    }

    /**
     * Converts the provided command string into a command object.
     * This can be a single command or several commands in a row.
     * The commands parsed must have been previously loaded through a call to loadCommands().
     * @param cmdText the string to be parsed
     * @return a command object parsed from the string
     * @throws ParserException if there are any syntax errors with the command string or any issues creating commands
     */
    public Command parse(String cmdText) throws ParserException {
        try {
            Scanner sc = new Scanner(cmdText);
            List<Command> list = new ArrayList<>();

            while (sc.hasNext()) {
                Optional<Command> item = parseToken(sc.next(), sc);
                item.ifPresent(list::add);
            }

            return list.size() == 1 ? list.get(0) : new CommandList(list);
        } catch(ParserException e) {
            throw e;
        } catch(Exception e) {
            throw newParserException("ParserUnknownException", e);
        }
    }

    /**
     * Parses the command that starts with the passed token.
     * The provided scanner will be positioned just after the end of the successfully parsed command after this method completes.
     * @param token the token to parse as a command
     * @param sc a scanner positioned just after the token passed
     * @return an optional containing the command parsed, or
     * @throws ParserException if any exceptions are encountered while parsing
     */
    public Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        for(AbstractParser parser : tokenParsers) {
            if(parser.canParse(token)) {
                return parser.parseToken(token, sc);
            }
        }

        throw newParserException("ParserTokenNotRecognized", token);
    }

}
