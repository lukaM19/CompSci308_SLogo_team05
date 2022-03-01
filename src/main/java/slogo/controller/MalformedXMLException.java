package slogo.controller;

/**
 * Custom exception thrown when the user attempts to load a malformed XML file.
 *
 * @author Luke McSween
 */
public class MalformedXMLException extends Exception {

    /**
     * Creates exception with given error message
     * @param errorMessage the error message to display
     */
    public MalformedXMLException(String errorMessage) {
        super(errorMessage);
    }}
