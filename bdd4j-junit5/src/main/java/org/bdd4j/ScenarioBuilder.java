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
  private List<Step<?>> steps = Collections.emptyList();

  /**
   * Creates a new instance.
   *
   * @param availableSteps The available steps.
   */
  public ScenarioBuilder(final T availableSteps) {
    this.availableSteps = availableSteps;
  }

  @SafeVarargs
  public final <S> void addSteps(Step<S>... steps) {
    this.steps = List.of(steps);
  }

  public T availableSteps() {
    return availableSteps;
  }

  public List<Step<?>> steps() {
    return steps;
  }
}
