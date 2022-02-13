package slogo.model;

import java.util.List;

public interface Model {
    List<MoveInfo> executeCommand(Command command);
}
