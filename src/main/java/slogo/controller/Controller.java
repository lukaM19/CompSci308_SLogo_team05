package slogo.controller;


import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import slogo.model.MoveInfo;
import slogo.view.MainView;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.io.File;

import slogo.command.general.Command;
import slogo.parser.Parser;
import slogo.model.Model;
import slogo.command.general.CommandResult;


/**
 * Controller class drives the SLogo program. Initializes and sets up view and model.
 *
 * @author Luke McSween
 */


public class Controller {

    private EventHandler<ActionEvent> saveHandler;
    private EventHandler<ActionEvent> loadHandler;
    private BiConsumer<ActionEvent, String> runHandler;
    private MainView myView;
    private Parser myParse;
    private Model myModel;


    public Controller(Stage stage, EventHandler<ActionEvent> newControllerHandler) {

        createEventHandlers();

        myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler, runHandler);
        myView.setUpView();
        myParse = new Parser();
        myModel = new Model();

        try {
            myParse.loadCommands("slogo.command");
        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private void createEventHandlers() {
        saveHandler = event -> save();
        loadHandler = event -> load();
        runHandler = (event, cmd) -> run(cmd);
    }


    private void save() {
        try {

        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
        return;
    }

    private void load() {
        return;
    }

    private void run(String commands) {
        try {
            Command cmd = myParse.parse(commands);
            List<MoveInfo> cmdresult =  myModel.executeCommand(cmd);
        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
        myView.handleMove(cmdresult);
    }
}
