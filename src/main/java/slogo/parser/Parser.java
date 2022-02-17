package slogo.parser;

import org.reflections.Reflections;
import slogo.Command;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Parser {
    private static final String PACKAGE_LOCATION = "/exceptions/";
    private static final String PACKAGE = "English";

    private final Reflections reflections;
    private HashMap<String, Class<? extends Command>> commands = new HashMap<>();
    private HashMap<Pattern, BiFunction<String, Scanner, Optional<Command>>> otherTokens = new HashMap<>() {{
        put(Pattern.compile("-?[0-9]+\\.?[0-9]*\n"), this::parseConstant);
        put(Pattern.compile(":[a-zA-Z]+", Pattern.CASE_INSENSITIVE), this::parseVariable);
        put(Pattern.compile("^#.*"), (s, sc) -> {
            sc.nextLine(); // Read all of rest of line TODO check with comments that have one word and no space between it and the #
            return Optional.empty();
        });
    }};
    private ResourceBundle resources;

    public Parser(String cmdPackage) throws ParserException {
        reflections = new Reflections(cmdPackage);
        resources = ResourceBundle.getBundle(PACKAGE_LOCATION + PACKAGE);
        loadCommands();
    }

    private void loadCommands() throws ParserException {
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(SlogoCommand.class);

        for(Class<?> commandClass : commandClasses) {
            if(!Command.class.isAssignableFrom(commandClass)) {
                throw newParserException("ParserClassNotExtendingCommand");
            }
            // Must have constructor with no arguments
            try {
                commandClass.getConstructor();
            } catch(Exception ex) {
                throw newParserException("ParserCommandNoDefaultConstructor");
            }

            commands.put(commandClass.getAnnotation(SlogoCommand.class).keyword(), commandClass.asSubclass(Command.class));
        }
    }

    public Command parse(String cmdText) throws ParserException {
        try {
            Scanner sc = new Scanner(cmdText);
            CommandList res = new CommandList();

            while (sc.hasNext()) {
                Optional<Command> item = parseToken(sc.next(), sc);
                item.ifPresent(command -> res.add(command));
            }

            return res;
        } catch(Exception e) {
            throw newParserException("ParserUnknownException");
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
            return Optional.of(parseCommand(token, sc));
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
            res = command.getConstructor().newInstance();
        } catch (Exception e) {
            throw newParserException("ParserExceptionWhileConstructingCommand");
        }
        res.setArguments(args);
        return res;
    }

    private Command parseList(Scanner sc) throws ParserException {
        CommandList list = new CommandList();
        String token = sc.next();
        while(!token.equals("]")) {
            Optional<Command> item = parseToken(token, sc);
            item.ifPresent(command -> list.add(command));
            token = sc.next();
        }

        return list;
    }

    private Optional<Command> parseOther(String token, Scanner sc) throws ParserException {
        for(Map.Entry<Pattern, BiFunction<String, Scanner, Optional<Command>>> entry : otherTokens.entrySet()) {
            if(entry.getKey().matcher(token).matches()) {
                return entry.getValue().apply(token, sc);
            }
        }

        throw newParserException("ParserTokenNotRecognized", token);
    }

    protected ResourceBundle getResources() {
        return resources;
    }

    protected ParserException newParserException(String msgKey, String...formatArgs) {
        return newParserException(msgKey, null, formatArgs);
    }

    protected ParserException newParserException(String msgKey, Exception cause, String...formatArgs) {
        return new ParserException(getResources().getString(msgKey).formatted(formatArgs), cause);
    }

}
