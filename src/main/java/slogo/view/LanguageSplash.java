package slogo.view;

import java.util.ResourceBundle;
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
  private String DEFAULT_CHOICE = "English";
  private String choice = DEFAULT_CHOICE;
  private String DEFAULT_FILEPATH = "/slogo/view/";
  private String CSS_STYLE = "Light.css";

  /**
   * Constructor for the splashscreen sets up the scene and sets css style, according to properties
   * file information.
   */
  public LanguageSplash() {
    myResources = ResourceBundle.getBundle(DEFAULT_FILEPATH + "LanguageChoice");
    myStage = new Stage();
    options = myResources.getString("languageOptions").split(",");
    optionsBox = new VBox();
    Label langLabel = new Label(myResources.getString("languagePrompt"));
    optionsBox.getChildren().add(langLabel);
    createOptions();
    this.setContent(optionsBox);
    Scene scene = new Scene(this);
    try {
      scene.getStylesheets().add(DEFAULT_FILEPATH + myResources.getString("cssPath"));
    } catch (Exception exception) {
      ErrorWindow errorWindow = new ErrorWindow(myResources.getString("cssError"));
      scene.getStylesheets().add(DEFAULT_FILEPATH + CSS_STYLE);
    }
    myStage.setScene(scene);


  }

  public String returnChoice() {
    myStage.showAndWait();
    return choice;
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
      myStage.close();
    });
    return result;
  }
}
