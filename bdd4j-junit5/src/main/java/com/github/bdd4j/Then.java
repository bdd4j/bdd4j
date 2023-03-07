package com.github.bdd4j;

import java.util.function.Function;

/**
 * A BDD step that can be used to perform assertions on the test state.
 *
 * @param description The human-readable description of the step.
 * @param logic       The logic used to perform the assertion.
 * @param <T>         The type of the test state.
 */
public record Then<T>(String description, Function<TestState<T>, TestState<T>> logic)
    implements Step<T>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final StepVisitor<T> visitor)
  {
    visitor.visit(this);
  }
}
