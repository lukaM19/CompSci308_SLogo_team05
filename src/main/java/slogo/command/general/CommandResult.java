package slogo.command.general;

import java.util.Optional;
import slogo.model.MoveInfo;

public record CommandResult(Double returnVal, Optional<? extends MoveInfo> moveInfo) {

}
