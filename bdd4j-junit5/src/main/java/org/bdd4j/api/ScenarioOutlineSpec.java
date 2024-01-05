package org.bdd4j.api;

public interface ScenarioOutlineSpec<S extends BDD4jSteps<TS>, TS> {

  /**
   * Applies this function to the given arguments.
   *
   * @param builder The builder
   * @param steps   The steps
   * @param row     The row
   * @return The scenario
   */
  BDD4jScenario<TS> apply(ScenarioBuilder<S, TS> builder, S steps, DataRow row);
}
