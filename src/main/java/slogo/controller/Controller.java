package slogo.controller;


import java.util.function.Consumer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import slogo.model.MoveInfo;
import slogo.view.MainView;
import java.util.Collection;
import java.util.List;
import java.io.File;

import slogo.command.general.Command;
import slogo.parser.Parser;
import slogo.model.Model;


/**
 * Controller class drives the SLogo program. Initializes and sets up view and model.
 *
 * @author Luke McSween
 */


public class Controller {

    private Runnable saveHandler;
    private EventHandler<ActionEvent> loadHandler;
    private Consumer<String> runHandler;
    private MainView myView;
    private Parser myParse;
    private Model myModel;
    private LogoSaver logosaver;
    private LogoLoader logoloader;
    private boolean LOGO_IN_PROGRESS;


    public Controller(Stage stage, EventHandler<ActionEvent> newControllerHandler) {

        createEventHandlers();

        myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler, runHandler);
        myView.setUpView();
        myParse = new Parser();
        myModel = new Model();
        logosaver = new LogoSaver();
        logoloader = new LogoLoader();
        LOGO_IN_PROGRESS = false;

        try {
            myParse.loadCommands("slogo.command");
        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private void createEventHandlers() {
        saveHandler = () -> save();
        loadHandler = event -> load();
        runHandler = cmd -> run(cmd);
    }


    private void save() {
        if (!LOGO_IN_PROGRESS) {
            return;
        }
        Collection<String> commandlist = myModel.getCommandHistory();
        File savefile = myView.chooseSaveFile();
        try {
            logosaver.saveLogo(commandlist, savefile);
        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private void load() {
        if (LOGO_IN_PROGRESS) {
            myView.showError("InvalidLoadException", "Cannot load a new logo while another logo is being edited.");
        }
        File loadfile = myView.chooseLoadFile();

        if (loadfile != null) {
            try {
                Collection<String> commands = logoloader.loadLogo(loadfile);
                for (String s : commands) {
                    run(s);
                }
            } catch (Exception e) {
                myView.showError(e.getClass().getCanonicalName(), e.getMessage());
            }
        }
    }

    private void run(String commands) {
        LOGO_IN_PROGRESS = true;
        try {
            Command cmd = myParse.parse(commands);
            List<MoveInfo> cmdresult =  myModel.executeCommand(cmd);
            myView.handleMove(cmdresult);
            myModel.saveCommands(commands);
        } catch (Exception e) {
            myView.showError(e.getClass().getCanonicalName(), e.getMessage());
        }
    }
}
