/**
 * Since the <code>VariablesView</code>, <code>CommandBoxHistory</code> and <code>UserFunctionsView</code> share the same characteristics they will extend this
 * abstract superclass which has all the basic functionalities. It has the methods to add a new entry,
 * clear, set Name to the infoDisplay etc/
 */
public interface UserInfoDisplay {

  /**
   * add a new entry to the display.
   */
  public void addEntry(){

  }

  /**
   * remove a particular entry from the list
   */
  public void removeEntry(){

  }

  /**
   * clears all entries.
   */
  public void clearDisplay(){

  }
}