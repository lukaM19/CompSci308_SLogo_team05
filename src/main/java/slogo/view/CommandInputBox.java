package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * A class which creates the command input box and governs its behaviour.
 * <p>
 * Depends on JavaFX and MainView.java.
 *
 * @author Luka Mdivani
 */
public class CommandInputBox extends BorderPane {

  public static final double COMMAND_BOX_HEIGHT = 230;
  private static final double COMMAND_BOX_WIDTH = 495;
  private TextArea myCommandBox = new TextArea();
  private Button runButton;
  private String commandsLog = "";
  private InfoDisplay myCommandHistoryBox;
  private VBox buttonBox;
  private ResourceBundle myResources;

  /**
   * Main constructor for the command input box
   *
   * @param commandHistory command history list;
   * @param runHandler     consumer which accepts the input in the controller, and then it is used
   *                       to pass the info to parsr.
   * @param resourceBundle the resource bundle.
   */
  public CommandInputBox(InfoDisplay commandHistory, Consumer<String> runHandler,
      ResourceBundle resourceBundle) {
    this.setMaxSize(COMMAND_BOX_WIDTH, COMMAND_BOX_HEIGHT);
    myResources = resourceBundle;
    myCommandBox.setId("myCommandBox");
    myCommandBox.setPrefHeight(COMMAND_BOX_WIDTH);
    myCommandBox.setOnKeyPressed(this::runShortcut);
    myCommandHistoryBox = commandHistory;
    createRunButton(runHandler);
    this.setCenter(myCommandBox);
    this.setLeft(buttonBox);

  }

  private void createRunButton(Consumer<String> runHandler) {
    runButton = new Button(myResources.getString("runPrompt"));
    runButton.setId("runButtonID");
    runButton.setOnAction(action -> {
      String inputString = getInput();
      runHandler.accept(inputString);
      myCommandHistoryBox.addToList(inputString);
    });

    buttonBox = new VBox(runButton);
    buttonBox.setId("myButtonBox");
  }

  private void logInput() {
    commandsLog += myCommandBox.getText();
    clear();
  }

  private String getInput() {
    logInput();
    String result = commandsLog;
    commandsLog = "";
    clear();
    return result;
  }

  private void clear() {
    myCommandBox.clear();
  }

  private void runShortcut(KeyEvent keyEvent) {
    if (keyEvent.isShortcutDown() && keyEvent.getCode() == KeyCode.ENTER) {
      runButton.fire();
    }
  }
}
