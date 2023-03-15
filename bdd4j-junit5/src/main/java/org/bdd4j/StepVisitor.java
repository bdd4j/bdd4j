package org.bdd4j;

/**
 * A visitor that can be used to visit the various types of steps and invoke step specific logic.
 *
 * @param <T> The type of the test state.
 */
public interface StepVisitor<T>
{
  /**
   * Retrieves the current state.
   *
   * @return The current state.
   */
  T currentState();

  /**
   * Visits the given step.
   *
   * @param step The step.
   */
  void visit(Given<T> step) throws Throwable;

  /**
   * Visits the given step.
   *
   * @param step The step.
   */
  void visit(When<T> step) throws Throwable;

  /**
   * Visits the given step.
   *
   * @param step The step.
   */
  void visit(Then<T> step);
}
