package org.bdd4j;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.TestReporter;

/**
 * The {@link ScenarioBuilder} provides access to available test {@link Step Steps} which can in turn be used
 * to set up the test scenario.
 * A test scenario is set up by adding steps using the addSteps method.
 *
 * @param <S>  The type of the steps.
 * @param <TS> The type of the test state.
 */
public final class ScenarioBuilder<S extends BDD4jSteps<TS>, TS> {
  private final S availableSteps;
  private List<Step<TS>> definedSteps = Collections.emptyList();
  private TestReporter testReporter;

  /**
   * Creates a new instance.
   *
   * @param availableSteps The available steps.
   */
  public ScenarioBuilder(final S availableSteps) {
    this.availableSteps = availableSteps;
  }

  /**
   * Defines the given steps as the ordered steps of the scenario.
   *
   * @param steps The steps that should be applied in order in the scenario.
   */
  @SafeVarargs
  public final void defineSteps(final Step<TS>... steps) {
    this.definedSteps = List.of(steps);
  }

  /**
   * Retrieves the steps that are available to the scenario.
   *
   * @return The available steps.
   */
  public S availableSteps() {
    return availableSteps;
  }

  /**
   * Sets the given test reporter for the builder.
   *
   * @param testReporter The test reporter.
   */
  public ScenarioBuilder<S, TS> withTestReporter(final TestReporter testReporter) {
    this.testReporter = testReporter;

    return this;
  }

  /**
   * Builds the scenario.
   *
   * @return The built scenario.
   */
  public BDD4jScenario<TS> build() {
    return new BDD4jScenario<>(availableSteps, testReporter, definedSteps);
  }
}
