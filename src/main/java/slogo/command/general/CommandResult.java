package slogo.command.general;

import java.util.List;

import slogo.model.MoveInfo;

public record CommandResult(Double returnVal, List<MoveInfo> moveInfos) {

}
