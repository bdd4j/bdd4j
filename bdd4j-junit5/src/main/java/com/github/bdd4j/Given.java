package com.github.bdd4j;

import java.util.function.Function;

/**
 * A BDD step that can be used to define some kind of state within the test scenario.
 *
 * @param description The human-readable description of the step.
 * @param logic       The logic used to prepare the state.
 * @param <T>         The type of the test state.
 */
public record Given<T>(String description, Function<T, TestState<T>> logic) implements Step<T>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(StepVisitor<T> visitor) throws Throwable
  {
    visitor.visit(this);
  }
}
