package slogo.controller;


import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.stage.Stage;
import slogo.model.MoveInfo;
import slogo.view.MainView;
import java.util.Collection;
import java.util.List;
import java.io.File;
import java.util.Map;

import slogo.command.general.Command;
import slogo.parser.SlogoParser;
import slogo.model.Model;


/**
 * Controller class drives the SLogo program. Initializes and sets up view and model.
 *
 * @author Luke McSween
 */


public class Controller {

    private Runnable saveHandler;
    private Runnable loadHandler;
    private Consumer<String> runHandler;
    private MainView myView;
    private SlogoParser myParse;
    private Model myModel;
    private LogoSaver logosaver;
    private LogoLoader logoloader;
    private boolean LOGO_IN_PROGRESS;
    private Map<String, Consumer<Object>> Consumermap;
    private String RESOURCE_PATH = "/exceptions/";
    private String DEFAULT_LANGUAGE = "English";
    private ResourceBundle myErrorResources;


    public Controller(Stage stage, Runnable newControllerHandler) {

        createEventHandlers();

        myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler, runHandler);
        myView.setUpView();

        myErrorResources = ResourceBundle.getBundle(RESOURCE_PATH + DEFAULT_LANGUAGE);
        Consumermap = myView.getConsumerMap();

        myParse = new SlogoParser();

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
        loadHandler = () -> load();
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
            myView.showError(e.getClass().getCanonicalName(), myErrorResources.getString(e.getClass().getCanonicalName()));
        }
    }

    private void load() {
        File loadfile = myView.chooseLoadFile();

        if (loadfile != null) {
            try {
                Collection<String> commands = logoloader.loadLogo(loadfile);
                for (String s : commands) {
                    System.out.print(s);
                    run(s);
                }
            } catch (Exception e) {
                myView.showError(e.getClass().getCanonicalName(), myErrorResources.getString(e.getClass().getCanonicalName()));
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

    public void setLanguage(String lang) {
        myParse.setLanguage(lang);
        myErrorResources = ResourceBundle.getBundle(RESOURCE_PATH + lang);
    }
}
