package slogo.command.test.bad;

import slogo.parser.SlogoCommand;

import java.util.List;

@SlogoCommand(keywords={"badtest"})
class TestCommandBadConstructor implements Command {
    public TestCommandBadConstructor(int arg1) {

    }

    @Override
    public void setArguments(List<Command> args) {

    }

    @Override
    public Object execute() {
        return 1;
    }
}
