package slogo.command.world;

import java.util.List;
import slogo.command.general.Command;

public abstract class WorldCommand extends Command {

  /***
   * Command object that acts on the World 
   *
   * @param parameters - the parameters for command
   */
  public WorldCommand(List<Command> parameters) {
    super(parameters);
  }
}
