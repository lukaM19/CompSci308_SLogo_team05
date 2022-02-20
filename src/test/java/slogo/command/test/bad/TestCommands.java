package slogo.command.test.bad;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.SlogoCommand;

import java.util.List;
import java.util.Map;

@SlogoCommand(keywords={"badtest"})
class TestCommandBadConstructor extends Command {
    public TestCommandBadConstructor(int arg1) {
        super(null);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {

    }

    @Override
    protected Double run() throws CommandException {
        return null;
    }
}
