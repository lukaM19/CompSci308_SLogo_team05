package slogo;

import java.util.Collection;
import java.util.List;

/***
 * The Command interface is the basis for all the different Command objects that will be used in
 * this project, such as fd or sum. This interface only has one public method called execute(),
 * which carries out a specific command based on the object. This class will allow the Parser to
 * run various actions on the World (model).
 */
public interface Command {

  void setArguments(List<Command> args);

  /***
   * The execute() method carries out a generic command: the specific implementation depends on the
   * loop body. Although the current design promises that all commands will return a Double, the
   * return type for execute() is an Object to be more flexible with future use cases.
   *
   * This method will throw syntax errors with a custom String describing what type of syntax error
   * happened (e.g. missing parameters, mispelled name, etc.).
   *
   * @return value designated by command
   */
  Object execute();
}
