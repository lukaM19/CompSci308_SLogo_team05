package slogo.command.test.good;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.parser.SlogoCommand;

import java.util.List;

@SlogoCommand(keywords = {"testnoargs"})
public class TestCommandNoArgs extends Command {
    public TestCommandNoArgs(List<Command> params) {
        super(params);
    }

    @Override
    protected void setUpExecution() throws CommandException {

    }

    @Override
    protected Double run() throws CommandException {
        return null;
    }
}
