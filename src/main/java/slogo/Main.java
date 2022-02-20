package slogo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import slogo.controller.Controller;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application{
    private static final String LANGUAGE_RESOURCE_PATH = "/slogo/languages/";
    private static final String EXAMPLE_PROGRAMS_PATH = "/examples";

    public static final String TITLE = "SLogo";

    private static List<Controller> myControllers;

    /**
     * Get command in a given language.
     */
    public String getCommand (String language, String command) {
        ResourceBundle resources = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + language);
        return resources.getString(command);
    }

    /**
     * Get first line of given example program.
     */
    public String getExampleProgram (String category, String example) {
        // regular expression representing one or more whitespace characters (space, tab, or newline)
        final String NEWLINE = "\\n+";

        List<String> lines = readFile(String.format("%s/%s/%s.slogo", EXAMPLE_PROGRAMS_PATH, category, example), NEWLINE);
        return lines.get(0).trim();
    }

    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    // this code is dense, hard to read, and throws exceptions so better to wrap in method
    private List<String> readFile (String filename, String delimiter) {
        try {
            String path = getClass().getResource(filename).toExternalForm();
            String data = new String(Files.readAllBytes(Paths.get(new URI(path))));
            return Arrays.asList(data.split(delimiter));
        }
        catch (URISyntaxException | IOException | NullPointerException e) {
            // NOT ideal way to handle exception, but this is just a simple test program
            System.out.println("ERROR: Unable to read input file " + e.getMessage());
            return Collections.emptyList();
        }
    }


    /**
     * Start of the program.
     */

    @Override
    public void start (Stage stage) {
        Main m = new Main();
        System.out.println(m.getVersion());
        System.out.println(m.getCommand("English", "Forward"));
        System.out.println(m.getExampleProgram("loops", "star"));

        myControllers = new ArrayList<>();
        myControllers.add(new Controller(stage, event -> addController()));
    }

    /**
     * Add a controller when the user clicks new window. Creates a new stage and passes it to new controller.
     */
    private void addController() {
        Stage newStage = new Stage();
        String numWindow = "" + myControllers.size() + 1;
        newStage.setTitle(TITLE + numWindow);
        newStage.show();
        myControllers.add(new Controller(newStage, event -> addController()));
    }
}
