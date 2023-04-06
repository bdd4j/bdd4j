package org.bdd4j;

import java.util.Collections;
import java.util.List;

/**
 * The {@link ScenarioBuilder} provides access to available test {@link Step Steps} which can in turn be used
 * to set up the test scenario.
 * A test scenario is set up by adding steps using the addSteps method.
 */
public final class ScenarioBuilder<T extends BDD4jSteps<?>> {
  private final T availableSteps;
  private List<Step<?>> definedSteps = Collections.emptyList();

  /**
   * Creates a new instance.
   *
   * @param availableSteps The available steps.
   */
  public ScenarioBuilder(final T availableSteps) {
    this.availableSteps = availableSteps;
  }

  /**
   * Defines the given steps as the ordered steps of the scenario.
   *
   * @param steps The steps that should be applied in order in the scenario.
   * @param <S>   The type of the steps.
   */
  @SafeVarargs
  public final <S> void defineSteps(final Step<S>... steps) {
    this.definedSteps = List.of(steps);
  }

  /**
   * Retrieves the steps that are available to the scenario.
   *
   * @return The available steps.
   */
  public T availableSteps() {
    return availableSteps;
  }

  /**
   * Returns the steps that are defined for this scenario.
   *
   * @return The defined steps of this scenario.
   */
  public List<Step<?>> definedSteps() {
    return definedSteps;
  }
}
