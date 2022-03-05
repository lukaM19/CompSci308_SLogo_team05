package slogo.parser;

import slogo.command.general.Command;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public abstract class AbstractParser {
    private static final String EXCEPTION_PACKAGE_LOCATION = "/exceptions/";
    protected static final String DEFAULT_LANGUAGE = "English";

    private ResourceBundle exceptionResources;

    /**
     * Creates a new AbstractParser, loading the resource bundles needed
     */
    public AbstractParser() {
        setLanguage(DEFAULT_LANGUAGE);
    }

    public void setLanguage(String lang) {
        exceptionResources = ResourceBundle.getBundle(EXCEPTION_PACKAGE_LOCATION + lang);
    }

    public abstract boolean canParse(String token);

    /**
     * Parses the command that starts with the passed token.
     * The provided scanner will be positioned just after the end of the successfully parsed command after this method completes.
     * @param token the token to parse as a command
     * @param sc a scanner positioned just after the token passed
     * @return an optional containing the command parsed, or
     * @throws ParserException if any exceptions are encountered while parsing
     */
    public abstract Optional<Command> parseToken(String token, Scanner sc) throws ParserException;

    /**
     * Finds and returns the first actual command (that is not a comment) using the passed token as a starting point.
     * The provided scanner will be positioned just after the end of the successfully parsed command after this method completes.
     * @param token the first token to try to parse as a command
     * @param sc a scanner positioned just after the token passed
     * @return the command parsed
     * @throws ParserException if any exceptions are encountered while parsing
     */
    public Command parseRequiredToken(String token, Scanner sc) throws ParserException {
        Optional<Command> res;
        while((res = parseToken(token, sc)).isEmpty()) {
            token = sc.next();
        }
        return res.get();
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
}
