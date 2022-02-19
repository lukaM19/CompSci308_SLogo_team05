package slogo.command.test.good;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.SlogoCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SlogoCommand(keywords={"testnoargs"})
class TestCommandNoArgs extends Command {
    public TestCommandNoArgs() {
        super(null);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {

    }

    @Override
    protected Object run() throws CommandException {
        return null;
    }
}

@SlogoCommand(keywords={"testonearg"}, arguments=1)
class TestCommandOneArg extends Command {
    private World world;

    public TestCommandOneArg() {
        super(Arrays.asList(new TestCommandNoArgs()));
    }

    @Override
    protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    }

    @Override
    protected Object run() throws CommandException {
        return parameters.get(0).execute(null,null);
    }
}
