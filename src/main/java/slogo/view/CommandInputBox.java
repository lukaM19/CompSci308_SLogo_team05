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
  private Button enterButton = new Button("Enter");
  private Button runButton = new Button("Run");
  private String commandsLog = "";

  public CommandInputBox(InfoDisplay commandHistory) {
    this.setMaxSize(COMMAND_BOX_WIDTH, COMMAND_BOX_HEIGHT);
    myCommandBox.setId("CommandBox");
    myCommandBox.setPrefHeight(COMMAND_BOX_WIDTH);
    enterButton.setOnAction(action -> {

    });
    runButton.setOnAction(action -> {
      String temp = getInput(); //FIXME
      commandHistory.addToList(temp);
    });
    myCommandBox.setOnKeyPressed(this::runShortcut);
    VBox buttonBox = new VBox(enterButton, runButton);
    this.setCenter(myCommandBox);
    this.setLeft(buttonBox);

  }

  private void logInput() {
    commandsLog += myCommandBox.getText() + "\n";
    clear();
  }

  public String getInput() {
    String result = commandsLog ;
    System.out.println(commandsLog );
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