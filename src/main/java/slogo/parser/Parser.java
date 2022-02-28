package slogo.parser;

import org.reflections.Configuration;
import org.reflections.Reflections;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;
import slogo.command.value.UserValue;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Parser {
    private static final String EXCEPTION_PACKAGE_LOCATION = "/exceptions/";
    private static final String COMMAND_PACKAGE_LOCATION = "/slogo/languages/";
    private static final String PACKAGE = "English";

    private Reflections reflections;
    private final HashMap<String, Class<? extends Command>> commands = new HashMap<>();
    private final HashMap<Pattern, BiFunction<String, Scanner, Optional<Command>>> otherTokens = new HashMap<>() {{
        put(Pattern.compile("-?[0-9]+\\.?[0-9]*"), (s, sc) -> parseConstant(s, sc));
        put(Pattern.compile(":[a-zA-Z]+", Pattern.CASE_INSENSITIVE), (s, sc) -> parseVariable(s, sc));
        put(Pattern.compile("^#.*"), (s, sc) -> {
            sc.nextLine(); // Discard the rest of the line, since it's all a comment
            return Optional.empty();
        });
    }};
    private final ResourceBundle exceptionResources;
    private final ResourceBundle cmdResources;

    /**
     * Creates a new Parser, initializing the various resources needed
     */
    public Parser() {
        exceptionResources = ResourceBundle.getBundle(EXCEPTION_PACKAGE_LOCATION + PACKAGE);
        cmdResources = ResourceBundle.getBundle(COMMAND_PACKAGE_LOCATION + PACKAGE);
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

    private void loadCommands(Reflections r) throws ParserException {
        reflections = r;
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(SlogoCommand.class);

        for(Class<?> commandClass : commandClasses) {
            if(!Command.class.isAssignableFrom(commandClass)) {
                throw newParserException("ParserClassNotExtendingCommand");
            }
            // Must have constructor taking argument commands
            try {
                commandClass.getDeclaredConstructor(List.class);
            } catch(Exception ex) {
                throw newParserException("ParserCommandNoValidConstructor", ex, commandClass.getName());
            }

            Arrays.stream(commandClass.getAnnotation(SlogoCommand.class).keywords()).forEach(
                    keywordID -> {
                        Arrays.stream(cmdResources.getString(keywordID).toLowerCase().split("\\|")).forEach(keyword -> {
                            commands.put(keyword.toLowerCase(), commandClass.asSubclass(Command.class));
                        });
                    });
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
            List<Command> list = new ArrayList<Command>();

            while (sc.hasNext()) {
                Optional<Command> item = parseToken(sc.next(), sc);
                item.ifPresent(list::add);
            }

            return new CommandList(list);
        } catch(ParserException e) {
            throw e;
        } catch(Exception e) {
            throw newParserException("ParserUnknownException", e);
        }
    }

    // Parses the first token that is not a comment
    private Command parseRequiredToken(String token, Scanner sc) throws ParserException {
        Optional<Command> res;
        while((res = parseToken(token, sc)).isEmpty()) {
            token = sc.next();
        }
        return res.get();
    }

    // Parses the next command and returns the result
    private Optional<Command> parseToken(String token, Scanner sc) throws ParserException {
        if(commands.containsKey(token.toLowerCase())) {
            return Optional.of(parseCommand(token.toLowerCase(), sc));
        } else if(token.equals("[")) {
            return Optional.of(parseList(sc));
        } else {
            return parseOther(token, sc);
        }
    }

    private Command parseCommand(String keyword, Scanner sc) throws ParserException {
        Class<? extends Command> command = commands.get(keyword);
        int argCount = command.getAnnotation(SlogoCommand.class).arguments();

        // Our arguments are commands too
        List<Command> args = new ArrayList<>(argCount);
        for(int i = 0; i < argCount; i++) {
            args.add(parseRequiredToken(sc.next(), sc));
        }

        Command res = null;
        try {
            res = command.getDeclaredConstructor(List.class).newInstance(args);
        } catch (Exception e) {
            throw newParserException("ParserExceptionWhileConstructingCommand");
        }
        res.setImpliedParameters(loadImpliedParameters(command, keyword));
        return res;
    }

    private Map<String, String> loadImpliedParameters(Class<? extends Command> command, String keyword) {
        Map<String, String> res = new HashMap<>();
        ImpliedArguments args = command.getAnnotation(ImpliedArguments.class);
        if(args == null) {
            return null;
        }
        for(var arg : args.value()) {
            for(String argKW : arg.keywords()) {
                if(Arrays.asList(cmdResources.getString(argKW).split("\\|")).contains(keyword)) {
                    res.put(arg.arg(), arg.value());
                    break;
                }
            }
        }

        return res;
    }

    private Command parseList(Scanner sc) throws ParserException {
        List<Command> list = new ArrayList<Command>();
        String token = sc.next();
        while(!token.equals("]")) {
            Optional<Command> item = parseToken(token, sc);
            item.ifPresent(list::add);
            token = sc.next();
        }

        return new CommandList(list);
    }

    private Optional<Command> parseOther(String token, Scanner sc) throws ParserException {
        for(Map.Entry<Pattern, BiFunction<String, Scanner, Optional<Command>>> entry : otherTokens.entrySet()) {
            if(entry.getKey().matcher(token).matches()) {
                return entry.getValue().apply(token, sc);
            }
        }

        throw newParserException("ParserTokenNotRecognized", token);
    }

    private Optional<Command> parseConstant(String token, Scanner sc) {
        return Optional.of(new GenericValue(Double.parseDouble(token)));
    }

    private Optional<Command> parseVariable(String token, Scanner sc) {
        return Optional.of(new UserValue(token.substring(1)));
    }

    protected ResourceBundle getExceptionResources() {
        return exceptionResources;
    }

    protected ParserException newParserException(String msgKey, String...formatArgs) {
        return newParserException(msgKey, null, formatArgs);
    }

    protected ParserException newParserException(String msgKey, Exception cause, String...formatArgs) {
        return new ParserException(getExceptionResources().getString(msgKey).formatted((Object[]) formatArgs), cause);
    }

    // Methods for use in testing only
    boolean hasCommand(String keyword) {
        return commands.containsKey(keyword);
    }

}
