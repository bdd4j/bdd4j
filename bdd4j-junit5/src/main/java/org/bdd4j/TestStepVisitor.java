package org.bdd4j;

/**
 * A visitor that can be used to execute step specific logic.
 *
 * @param <T> The type of the test state.
 */
public final class TestStepVisitor<T> implements StepVisitor<T>
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
  public T currentState()
  {
    return state.state();
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
