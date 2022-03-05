package slogo.model;

@FunctionalInterface
public interface DoubleLambda {

  /***
   * Runs the given lambda on the actor
   *
   * @return double result of the method execution
   */
  double execute();
}
