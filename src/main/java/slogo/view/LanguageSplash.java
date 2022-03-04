package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A class for selecting the language of GUI before starting it. Depends on JavaFX.
 *
 * @author Luka Mdivani
 */
public class LanguageSplash extends ScrollPane {

  private ResourceBundle myResources;
  private Stage myStage;
  private VBox optionsBox;
  private String[] options;
  private final static String DEFAULT_CHOICE = "English";
  private String choice = DEFAULT_CHOICE;
  private final static String DEFAULT_FILEPATH = "/slogo/view/";
  private String CSS_STYLE = "Light.css";
  private Runnable mainViewSetUp;
  private Consumer<String> myLanguageSetter;

  /**
   * Constructor for the splashscreen sets up the scene and sets css style, according to properties
   * file information.
   */
  public LanguageSplash(Stage primaryStage, Runnable setUp, Consumer<String> languageSetter) {
    myResources = ResourceBundle.getBundle(DEFAULT_FILEPATH + "LanguageChoice");
    myStage = primaryStage;
    options = myResources.getString("languageOptions").split(",");
    optionsBox = new VBox();
    optionsBox.setId("optionsBox");
    mainViewSetUp = setUp;
    myLanguageSetter = languageSetter;

    makeLabel();
    createOptions();
    this.setContent(optionsBox);
    Scene myScene = new Scene(this);
    try {
      myScene.getStylesheets().add(DEFAULT_FILEPATH + myResources.getString("cssPath"));
    } catch (Exception exception) {
      ErrorWindow errorWindow = new ErrorWindow(myResources.getString("cssError"));
      myScene.getStylesheets().add(DEFAULT_FILEPATH + CSS_STYLE);
    }
    myStage.setScene(myScene);
    myStage.show();


  }

  private void makeLabel() {
    Label langLabel = new Label(myResources.getString("languagePrompt"));
    langLabel.setId("languageChoicePrompt");
    optionsBox.getChildren().add(langLabel);
  }


  private void createOptions() {
    for (String option : options) {
      Button result = makeButton(option);
      result.setId(option);
      optionsBox.getChildren().addAll(result);

    }
  }

  private Button makeButton(String language) {
    Button result = new Button();
    result.setId(language + "Button");
    result.setText(language);
    result.setOnAction(e -> {
      choice = result.getText();
      myLanguageSetter.accept(choice);
      myStage.close();
      mainViewSetUp.run();
    });
    return result;
  }
}
