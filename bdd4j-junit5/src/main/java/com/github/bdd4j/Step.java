package com.github.bdd4j;

/**
 * The common interface for objects that represent a step.
 *
 * @param <T> The type of the test state.
 */
public interface Step<T>
{
  /**
   * A human-readable description for the step.
   *
   * @return The description.
   */
  String description();

  /**
   * Accepts the given visitor and invokes its step specific logic.
   *
   * @param visitor The visitor that should be accepted.
   * @throws Throwable Might throw any kind of exception.
   */
  void accept(final StepVisitor<T> visitor) throws Throwable;
}
