package slogo.parser;

import slogo.command.general.Command;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.ImpliedArguments;

import java.util.ResourceBundle;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


class CommandParser extends AbstractParser {
    /**
     * All of the details needed to instantiate a command class
     */
    private record CommandDetails(Class<? extends Command> cmdClass, int argCount) {}

    private static final String COMMAND_PACKAGE_LOCATION = "/slogo/languages/";
    private static final String DEFAULT_PACKAGE = "English";

    private ResourceBundle cmdResources;
    private final Map<String, CommandDetails> commands = new HashMap<>();
    private AbstractParser argumentParser;

    /**
     * Initializes this CommandParser
     * @param argParser the parser to use for each argument to the command
     */
    public CommandParser(AbstractParser argParser) {
        argumentParser = argParser;
        cmdResources = ResourceBundle.getBundle(COMMAND_PACKAGE_LOCATION + DEFAULT_PACKAGE);
    }

    /**
     * Sets the language to use for parsing commands
     * @param lang the language to use
     */
    public void setLanguage(String lang) {
        super.setLanguage(lang);
        cmdResources = ResourceBundle.getBundle(COMMAND_PACKAGE_LOCATION + lang);
    }

    /**
     * Sets the parser to be used for parsing arguments
     * @param parser the parser to use for parsing arguments
     */
    public void setArgumentParser(AbstractParser parser) {
        argumentParser = parser;
    }

    /**
     * Returns if a command with the specified keyword has been registered. Ignores case.
     * @param keyword the keyword of the command to check for
     * @return whether the command is registered
     */
    @Override
    public boolean canParse(String keyword) {
        return commands.containsKey(keyword.toLowerCase());
    }

    /**
     * Parses a single command and its arguments, returning the result as a Command object.
     * When parsing is successfully completed, the scanner object provided will be positioned just after the last argument of the command.
     * @param keyword the keyword for the command to be constructed
     * @param sc a scanner positioned at just after the command keyword
     * @return the command parsed
     * @throws ParserException if an error occurs while parsing the command
     * @throws NullPointerException if the argument parser has been initialized to null
     */
    @Override
    public Optional<Command> parseToken(String keyword, Scanner sc) throws ParserException, NullPointerException {
        CommandDetails command = commands.get(keyword.toLowerCase());

        // Our arguments are commands too
        List<Command> args = new ArrayList<>(command.argCount());
        for(int i = 0; i < command.argCount(); i++) {
            String token = sc.next();
            if(!argumentParser.canParse(token)) {
                throw newParserException("ParserTokenNotRecognized", token);
            }
            args.add(argumentParser.parseRequiredToken(token, sc));
        }

        Command res;
        try {
            res = command.cmdClass.getDeclaredConstructor(List.class).newInstance(args);
        } catch (Exception e) {
            throw newParserException("ParserExceptionWhileConstructingCommand", e);
        }
        res.setImpliedParameters(loadImpliedParameters(command.cmdClass, keyword));
        return Optional.of(res);
    }

    /**
     * Registers the provided class as a Command.
     * All classes provided must contain a constructor taking a List of Commands.
     * @param keywordID the resource ID of the keywords to be used for this command
     * @param commandClass the class to use for this command
     * @param args the number of arguments this command takes
     */
    public void registerCommand(String keywordID, Class<? extends Command> commandClass, int args) {
        for(String keyword : cmdResources.getString(keywordID).split("\\|")) {
            commands.put(keyword.toLowerCase(), new CommandDetails(commandClass, args));
        }
    }

    private Map<String, String> loadImpliedParameters(Class<? extends Command> command, String keyword) {
        Map<String, String> res = new HashMap<>();
        ImpliedArguments args = command.getAnnotation(ImpliedArguments.class);
        if(args == null) {
            return res;
        }

        for(ImpliedArgument arg : args.value()) {
            for(String argKW : arg.keywords()) {
                if(Arrays.asList(cmdResources.getString(argKW).split("\\|")).contains(keyword)) {
                    res.put(arg.arg(), arg.value());
                    break;
                }
            }
        }

        return res;
    }

}
