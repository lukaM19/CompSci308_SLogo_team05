package slogo.command.interpreter;

import slogo.command.general.Command;

public class ParameterNumberChecker {
  private String commandName;
  private int additionalParametersNeeded;
  private int totalParameterNumber;

  public ParameterNumberChecker(String commandName, int additionalParametersNeeded) {
    this.commandName = commandName;
    this.additionalParametersNeeded = additionalParametersNeeded;
    this.totalParameterNumber = additionalParametersNeeded;
  }

  public boolean parameterNumberReached() {
    return additionalParametersNeeded == 0;
  }

  public void decrementAPN() {
    additionalParametersNeeded--;
  }

  public String getCommandName() {
    return commandName;
  }

  public int getTotalParameterNumber() {
    return totalParameterNumber;
  }

  @Override
  public String toString() {
    return "ParameterNumberChecker: " + commandName;
  }
}
