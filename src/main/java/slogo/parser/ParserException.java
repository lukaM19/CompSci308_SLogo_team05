package slogo.parser;

/**
 * Wraps exceptions thrown by the parser.
 *
 * @author Ricky Weerts
 */
public class ParserException extends Exception {

    /**
     * Create a ParserException with just a custom message
     *
     * @param msg the message attached to this exception
     */
    public ParserException(String msg) {
        super(msg);
    }

    /**
     * Create a ParserException with a custom message and an exception that caused it
     *
     * @param msg   the message attached to this exception
     * @param cause the exception that caused this ParserException to be thrown
     */
    public ParserException(String msg, Exception cause) {
        super(msg, cause);
    }

}
