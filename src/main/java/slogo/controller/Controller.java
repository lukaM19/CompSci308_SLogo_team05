package slogo.controller;


import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import slogo.view.MainView;


/**
 * Controller class drives the SLogo program. Initializes and sets up view and model.
 *
 * @author Luke McSween
 */


public class Controller {


    private EventHandler<ActionEvent> saveHandler;
    private EventHandler<ActionEvent> loadHandler;


    public Controller(Stage stage, EventHandler<ActionEvent> newControllerHandler) {

        createEventHandlers();

        MainView myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler);

    }

    private void createEventHandlers() {
        saveHandler = event -> save();
        loadHandler = event -> load();
    }



    private void save() {

    }

    private void load() {

    }
}
