package slogo.commandtest.bad.test2;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

import java.util.Map;

@SlogoCommand(keywords={"badtest"})
public class TestCommandDoesntExtendCommand {


    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {

    }


    protected Double run() throws CommandException {
        return null;
    }
}
