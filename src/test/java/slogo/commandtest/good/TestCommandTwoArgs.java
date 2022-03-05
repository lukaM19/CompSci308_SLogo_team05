package slogo.commandtest.good;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SlogoCommand(keywords={"testtwoargs"}, arguments=2)
public class TestCommandTwoArgs extends Command {
    private World world;

    public TestCommandTwoArgs(List<Command> params) {
        super(params);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    }

    @Override
    protected Double run() throws CommandException {
        return executeParameter(0, null,null).returnVal() - executeParameter(1, null,null).returnVal();
    }
}
