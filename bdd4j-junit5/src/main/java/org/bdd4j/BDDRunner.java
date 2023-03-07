package org.bdd4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A runner that can be used to execute BDD scenarios.
 */
public class BDDRunner
{
  /**
   * Creates a new scenario.
   *
   * @param stepsWrapper The steps instance.
   * @param steps        The steps that should be executed.
   * @param <T>          The type of the test state.
   */
  @SafeVarargs
  public static <T> void scenario(final BDD4jSteps<T> stepsWrapper, final Step<T>... steps)
  {
    try
    {
      final TestStepVisitor<T> stepVisitor = new TestStepVisitor<>(stepsWrapper.init());

      for (final Step<T> step : steps)
      {
        final LocalDateTime timestamp = LocalDateTime.now();
        System.out.println(
            "> Running step: " + step.getClass().getSimpleName() + " " + step.description());

        step.accept(stepVisitor);

        final long executionTime = timestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS);

        System.out.println(
            "> Completed step: " + step.getClass().getSimpleName() + " " + step.description() +
                " in " +
                executionTime + "ms");
      }

      System.out.println();
    } catch (final AssertionError e)
    {
      throw e;
    } catch (final Throwable e)
    {
      throw new AssertionError("Failed to execute the scenario", e);
    }
  }

  /**
   * A visitor that can be used to execute step specific logic.
   *
   * @param <T> The type of the test state.
   */
  private static class TestStepVisitor<T> implements StepVisitor<T>
  {

    private TestState<T> state;

    /**
     * Creates a new instance.
     *
     * @param initialState The initial state of the test.
     */
    public TestStepVisitor(final TestState<T> initialState)
    {
      this.state = initialState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Given<T> step) throws Throwable
    {
      if (state.exception() != null)
      {
        throw state.exception();
      }

      try
      {
        state = step.logic().apply(state.state());
      } catch (final Throwable exception)
      {
        state = TestState.exception(exception);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final When<T> step) throws Throwable
    {
      if (state.exception() != null)
      {
        throw state.exception();
      }

      try
      {
        state = step.logic().apply(state.state());
      } catch (final Throwable exception)
      {
        state = TestState.exception(exception);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Then<T> step)
    {
      try
      {
        state = step.logic().apply(state);
      } catch (final AssertionError assertionError)
      {
        throw assertionError;
      } catch (final Throwable exception)
      {
        state = TestState.exception(exception);
      }
    }
  }
}
