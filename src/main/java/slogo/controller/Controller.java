package slogo.controller;


import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import slogo.view.MainView;
import java.util.Collection;
// import slogo.parser.Parser;


/**
 * Controller class drives the SLogo program. Initializes and sets up view and model.
 *
 * @author Luke McSween
 */


public class Controller {


    private EventHandler<ActionEvent> saveHandler;
    private EventHandler<ActionEvent> loadHandler;
    private EventHandler<ActionEvent> runHandler;
    private MainView myView;
    private Parser myParse;


    public Controller(Stage stage, EventHandler<ActionEvent> newControllerHandler) {

        createEventHandlers();

        myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler);
        myView.setUpView();
        myParse = new Parser();
        try {
            myParse.loadCommands("slogo.command");
        } catch (ParserException e) {

        }



    }

    private void createEventHandlers() {
        saveHandler = event -> save();
        loadHandler = event -> load();
        runHandler = event -> run();
    }



    private void save() {

    }

    private void load() {

    }

    private void run(String commands) {
        try {
            myParser.parse(commands);
        } catch (ParserException e) {

        }

        
    }
}
