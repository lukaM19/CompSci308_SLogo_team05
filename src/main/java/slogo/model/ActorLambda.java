package slogo.model;

@FunctionalInterface
public interface ActorLambda<T extends Actor> {

  /***
   * Runs the given lambda on the actor
   *
   * @param actor to run the lambda on
   * @return double result of the execution
   */
  double runMethodOnActor(Actor actor);
}
