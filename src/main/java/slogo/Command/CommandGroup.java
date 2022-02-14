package slogo.Command;

import java.util.List;
import slogo.model.World;

public class CommandGroup extends Command {

  public CommandGroup(World world, List<Command> parameters) {
    super(world, parameters);
  }

  @Override
  public Object execute() {
    for(int i=0; i<getParametersSize() - 1; i++) {
      parameters.get(i).execute();
    }
    return parameters.get(getParametersSize() - 1).execute();
  }
}
