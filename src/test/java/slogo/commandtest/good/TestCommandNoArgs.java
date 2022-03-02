package slogo.commandtest.good;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

import java.util.List;
import java.util.Map;

@SlogoCommand(keywords = {"testnoargs"})
public class TestCommandNoArgs extends Command {
    public TestCommandNoArgs(List<Command> params) {
        super(params);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {

    }

    @Override
    protected Double run() throws CommandException {
        return null;
    }
}
