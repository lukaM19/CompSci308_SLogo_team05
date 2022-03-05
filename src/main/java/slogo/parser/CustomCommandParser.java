package slogo.parser;

import slogo.command.custom.CreateCustomCommand;
import slogo.command.custom.RunCustomCommand;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.Assigment;
import slogo.command.value.GenericValue;
import slogo.command.value.UserValue;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.ImpliedArguments;

import java.util.*;
import java.util.regex.Pattern;


public class CustomCommandParser extends AbstractParser {
    /**
     * All of the details needed to instantiate a custom command
     */
    private record CommandDetails(CommandList arguments, CommandList body) {}

    private static final String COMMAND_PACKAGE_LOCATION = "/slogo/languages/";
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z_]+(\\?)?$");

    private List<String> newCommandKeywords;
    private final Map<String, CommandDetails> customCommands = new HashMap<>();
    private AbstractParser argumentParser;
    private CommandParser builtInCommandParser;

    /**
     * Initializes this CustomCommandParser
     * @param argParser the parser to use for the body and arguments of custom commands
     */
    public CustomCommandParser(AbstractParser argParser, CommandParser commandParser) {
        this.argumentParser = argParser;
        builtInCommandParser = commandParser;
        setLanguage(DEFAULT_LANGUAGE);
    }

    /**
     * Sets the language to use for parsing the "to" command
     * @param lang the language to use
     */
    public void setLanguage(String lang) {
        super.setLanguage(lang);
        newCommandKeywords = Arrays.asList(ResourceBundle.getBundle(COMMAND_PACKAGE_LOCATION + lang).getString("MakeUserInstruction").split("\\|"));
    }

    /**
     * Sets the parsers to be used for parsing the body of custom commands and checking if commands are already built-in
     * @param argParser the parser to use for parsing the body and arguments of custom commands
     * @param commandParser the parser to use for checking if commands already exist
     */
    public void setParsers(AbstractParser argParser, CommandParser commandParser) {
        this.argumentParser = argParser;
        builtInCommandParser = commandParser;
    }

    /**
     * Returns if a custom command with the specified keyword has been registered (or if this is a "to" command). Ignores case.
     * @param keyword the keyword of the command to check for
     * @return whether the custom command is registered or this is a "to" command
     */
    @Override
    public boolean canParse(String keyword) {
        return customCommands.containsKey(keyword.toLowerCase()) || newCommandKeywords.contains(keyword.toLowerCase());
    }

    /**
     * Parses a single custom command or custom command registration, returning the result as a Command object.
     * When parsing is successfully completed, the scanner object provided will be positioned just after the end of the command.
     * @param keyword the keyword for the command to be constructed or the "to" keyword
     * @param sc a scanner positioned at just after the command keyword
     * @return the command parsed
     * @throws ParserException if an error occurs while parsing the command
     * @throws NullPointerException if the body parser has been initialized to null
     */
    @Override
    public Optional<Command> parseToken(String keyword, Scanner sc) throws ParserException, NullPointerException {
        if(newCommandKeywords.contains(keyword.toLowerCase())) {
            return Optional.of(parseNewCustomCommand(sc.next(), sc));
        } else if(customCommands.containsKey(keyword.toLowerCase())) {
            return Optional.of(parseRunCustomCommand(keyword.toLowerCase(), sc));
        } else {
            throw newParserException("ParserTokenNotRecognized", keyword);
        }
    }

    private Command parseNewCustomCommand(String keyword, Scanner sc) throws ParserException {
        if(!pattern.matcher(keyword).matches() && !builtInCommandParser.canParse(keyword)) {
            return new GenericValue(0d);
        }
        Optional<Command> argList;
        String argToken = sc.next();
        if(!argumentParser.canParse(argToken) || (argList = argumentParser.parseToken(argToken, sc)).isEmpty()) {
            throw newParserException("ParserIllegalCustomCommandFirstArgument");
        }
        CommandList args = verifyArgument(argList.get(), true);

        Optional<Command> bodyList;
        String bodyToken = sc.next();
        if(!argumentParser.canParse(bodyToken) || (bodyList = argumentParser.parseToken(bodyToken, sc)).isEmpty()) {
            throw newParserException("ParserIllegalCustomCommandSecondArgument");
        }
        CommandList body = verifyArgument(bodyList.get(), false);

        return new CreateCustomCommand(this, keyword, args, body);
    }

    private CommandList verifyArgument(Command command, boolean allVars) throws ParserException {
        if(!(command instanceof CommandList res)) {
            throw newParserException("ParserIllegalCustomCommandFirstArgument");
        }

        if(!allVars) {
            return res;
        }

        for(int i = 0; i < res.getParametersSize(); i++) {
            if(!(res.getParameterCommand(i) instanceof UserValue)) {
                throw newParserException("ParserIllegalCustomCommandFirstArgument");
            }
        }

        return res;
    }

    private Command parseRunCustomCommand(String keyword, Scanner sc) throws ParserException {
        CommandDetails commandDetails = customCommands.get(keyword);
        List<Command> args = new ArrayList<>();
        for(int i = 0; i < commandDetails.arguments().getParametersSize(); i++) {
            args.add(new Assigment(List.of(commandDetails.arguments().getParameterCommand(i), argumentParser.parseRequiredToken(sc.next(), sc))));
        }

        return new RunCustomCommand(List.of(new CommandList(args), commandDetails.body));
    }

    /**
     * Registers a custom command.
     * @param keyword the keyword to be used for this command
     * @param args the argument variables this command takes in
     * @param body the commands in the body of this command
     */
    public void registerCommand(String keyword, CommandList args, CommandList body) {
        customCommands.put(keyword.toLowerCase(), new CommandDetails(args, body));
    }

}
