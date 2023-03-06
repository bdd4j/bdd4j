package bdd4j;

/**
 * This is an abstract base class that needs to be
 */
public interface BDD4jSteps<T>
{
  /**
   * Initializes the state for the test.
   *
   * @return The state.
   */
  TestState<T> init();
}
