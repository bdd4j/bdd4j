package org.bdd4j.api;

import org.bdd4j.internal.BDD4jParameterResolver;

/**
 * This is the common interface that needs to be implemented by classes that provide access to
 * the steps used in bdd4j scenarios.
 * <p>
 * Implementations should define factory methods for the {@link Given}, {@link When} and
 * {@link Then} steps for the scenarios, by using the builders defined in the {@link StepDSL} class
 * and exposing them to tests.
 * <p>
 * To initialize the test state the interface provides the {@link BDD4jSteps#init(Parameters)} method,
 * which must be implemented by concrete step classes.
 * <p>
 * Instances of the steps can be injected into the scenario test methods. This is done by the
 * {@link BDD4jParameterResolver}.
 *
 * @param <T> The type of the state managed by the BDD4j test.
 */
public interface BDD4jSteps<T> {
  /**
   * Initializes the state for the test.
   *
   * @param parameters The parameters used to initialize the test state.
   * @return The state.
   */
  TestState<T> init(Parameters parameters);
}
