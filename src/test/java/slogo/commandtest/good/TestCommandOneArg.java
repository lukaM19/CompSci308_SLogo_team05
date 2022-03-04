package slogo.commandtest.good;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SlogoCommand(keywords={"testonearg"}, arguments=1)
public class TestCommandOneArg extends Command {
    private World world;

    public TestCommandOneArg(List<Command> params) {
        super(params);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    }

    @Override
    protected Double run() throws CommandException {
        return executeParameter(0, null,null).returnVal();
    }
}
