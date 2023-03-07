package org.bdd4j;

/**
 * A datatype that can be used to represent the state for a BDD test.
 *
 * @param state     The regular state held by the test.
 * @param exception An optional exception that has been thrown by a previous step.
 * @param <T>       The type of the test state.
 */
public record TestState<T>(T state, Throwable exception)
{
  /**
   * Creates a new test state with the given state.
   *
   * @param state The state.
   * @param <T>   The type of the test state.
   * @return The test state.
   */
  public static <T> TestState<T> state(final T state)
  {
    return new TestState<>(state, null);
  }

  /**
   * Creates a new test state with the given exception.
   *
   * @param exception The exception.
   * @param <T>       The type of the test state.
   * @return The test state.
   */
  public static <T> TestState<T> exception(final Throwable exception)
  {
    return new TestState<>(null, exception);
  }

  /**
   * Creates a copy of the test state with the given exception.
   *
   * @param exception The exception.
   * @return The test state.
   */
  public TestState<T> withException(final Throwable exception)
  {
    return new TestState<>(state, exception);
  }

  /**
   * Creates a copy of the test state with the given state.
   *
   * @param state The state.
   * @return The test state.
   */
  public TestState<T> withState(final T state)
  {
    return new TestState<>(state, exception);
  }
}
