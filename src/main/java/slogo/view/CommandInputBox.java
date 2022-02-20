package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CommandInputBox extends BorderPane {

  public static final double COMMAND_BOX_HEIGHT = 200;
  private static final double COMMAND_BOX_WIDTH = 450;
  private TextArea myCommandBox = new TextArea();
  private Button enterButton ;
  private Button runButton  ;
  private String commandsLog = "";
  private InfoDisplay myCommandHistoryBox;
  private VBox buttonBox ;

  public CommandInputBox(InfoDisplay commandHistory) {
    this.setMaxSize(COMMAND_BOX_WIDTH, COMMAND_BOX_HEIGHT);
    myCommandBox.setId("myCommandBox");
    myCommandBox.setPrefHeight(COMMAND_BOX_WIDTH);
    myCommandBox.setOnKeyPressed(this::runShortcut);
    myCommandHistoryBox=commandHistory;
    createButton();
    this.setCenter(myCommandBox);
    this.setLeft(buttonBox);

  }

  private void createButton(){
    runButton = new Button("Run");
    runButton.setId("myRunButton");
    runButton.setOnAction(action -> {
      String temp = getInput(); //FIXME
      myCommandHistoryBox.addToList(temp);
    });

    buttonBox = new VBox(runButton);
    buttonBox.setId("myButtonBox");
  }

  private void logInput() {
    commandsLog += myCommandBox.getText() + "\n";
    clear();
  }

  public String getInput() {
    logInput();
    String result = commandsLog ;
    commandsLog = "";
    clear();
    return result;
  }

  public void clear() {
    myCommandBox.clear();
  }

  private void runShortcut(KeyEvent keyEvent) {
    if (keyEvent.isShortcutDown() && keyEvent.getCode() == KeyCode.ENTER) {
      runButton.fire();
    }
  }
}
