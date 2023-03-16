package org.bdd4j;

import java.util.function.Function;

/**
 * A BDD step that can be used to invoke some kind of action in the test scenario.
 *
 * @param description The human-readable description of the step.
 * @param logic       The logic used to invoke the step.
 * @param <T>         The type of the test state.
 */
public record When<T>(String description, Function<T, TestState<T>> logic) implements Step<T>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(StepVisitor<T> visitor) throws Throwable
  {
    visitor.visit(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TestState<T> applyLogic(final TestState<T> state) throws Throwable
  {
    state.raiseExceptionIfPresent();

    try
    {
      return logic().apply(state.state());
    } catch (final Throwable exception)
    {
      return TestState.exception(exception);
    }
  }
}
