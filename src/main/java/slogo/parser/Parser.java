package slogo.parser;

import org.reflections.Reflections;
import slogo.Command;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Parser {
    private Reflections reflections = new Reflections("");
    private HashMap<String, Class<? extends Command>> commands = new HashMap<>();
    private HashMap<Pattern, BiFunction<String, Scanner, Optional<Command>>> otherTokens = new HashMap<>() {{
        put(Pattern.compile("-?[0-9]+\\.?[0-9]*\n"), this::parseConstant);
        put(Pattern.compile(":[a-zA-Z]+", Pattern.CASE_INSENSITIVE), this::parseVariable);
        put(Pattern.compile("^#.*"), (s, sc) -> {
            sc.nextLine(); // Read all of rest of line TODO check with comments that have one word and no space between it and the #
            return Optional.empty();
        });
    }};

    public Parser() {
        loadCommands();
    }

    private void loadCommands() {
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(SlogoCommand.class);

        for(Class<?> commandClass : commandClasses) {
            if(!Command.class.isAssignableFrom(commandClass)) {
                throw new ParserException();
            }
            // Must have constructor with no arguments
            try {
                commandClass.getConstructor();
            } catch(Exception ex) {
                throw new ParserException();
            }

            commands.put(commandClass.getAnnotation(SlogoCommand.class).keyword(), commandClass.asSubclass(Command.class));
        }
    }

    public Command parse(String cmdText) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(cmdText);
        CommandList res = new CommandList();

        while(sc.hasNext()) {
            Optional<Command> item = parseToken(sc.next(), sc);
            item.ifPresent(command -> res.add(command));
        }

        return res;
    }

    // Parses the first token that is not a comment
    private Command parseRequiredToken(String token, Scanner sc) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Optional<Command> res;
        while((res = parseToken(token, sc)).isEmpty()) {
            token = sc.next();
        }
        return res.get();
    }

    // Parses the next command and returns the result
    private Optional<Command> parseToken(String token, Scanner sc) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(commands.containsKey(token.toLowerCase())) {
            return Optional.of(parseCommand(token, sc));
        } else if(token.equals("[")) {
            return Optional.of(parseList(sc));
        } else {
            return parseOther(token, sc);
        }
    }

    private Command parseCommand(String keyword, Scanner sc) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<? extends Command> command = commands.get(keyword);
        int argCount = command.getAnnotation(SlogoCommand.class).arguments();

        // Our arguments are commands too
        List<Command> args = new ArrayList<>(argCount);
        for(int i = 0; i < argCount; i++) {
            args.add(parseRequiredToken(sc.next(), sc));
        }

        Command res = command.getConstructor().newInstance();
        res.setArguments(args);
        return res;
    }

    private Command parseList(Scanner sc) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CommandList list = new CommandList();
        String token = sc.next();
        while(!token.equals("]")) {
            Optional<Command> item = parseToken(token, sc);
            item.ifPresent(command -> list.add(command));
            token = sc.next();
        }

        return list;
    }

    private Optional<Command> parseOther(String token, Scanner sc) {
        for(Map.Entry<Pattern, BiFunction<String, Scanner, Optional<Command>>> entry : otherTokens.entrySet()) {
            if(entry.getKey().matcher(token).matches()) {
                return entry.getValue().apply(token, sc);
            }
        }

        throw new ParserException();
    }

}
