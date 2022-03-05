package slogo.commandtest.bad;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

import java.util.Map;

@SlogoCommand(keywords={"badtest"})
public class TestCommandBadConstructor extends Command {
    public TestCommandBadConstructor(int arg1) {
        super(null);
    }

    @Override
    protected void setUpExecution() throws CommandException {

    }

    @Override
    protected Double run() throws CommandException {
        return null;
    }
}
