/**
 * the text box object from javafx where user puts the desired commands, and they are later returned
 * as a String when needed(Run button is hit)
 */
public interface CommandInputBox {

  /**
   * @return the string of the input entered by the user
   */
  public String getInput() {
  }

}