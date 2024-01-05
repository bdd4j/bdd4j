package org.bdd4j.internal;

import java.lang.reflect.Method;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;

/**
 * The ScenarioNameGenerator class is responsible for generating the name of a scenario based on annotations present on the given method.
 */
public class ScenarioNameGenerator {

  /**
   * Generates the name of a scenario based on annotations present on the given method.
   *
   * @param scenario The method representing the scenario.
   * @return The name of the scenario.
   */
  public static String generateName(final Method scenario) {
    final String scenarioName;

    if (scenario.isAnnotationPresent(Scenario.class)) {
      scenarioName = scenario.getAnnotation(Scenario.class).value();
    } else if (scenario.isAnnotationPresent(ScenarioOutline.class)) {
      scenarioName = scenario.getAnnotation(ScenarioOutline.class).description();
    } else {
      scenarioName = scenario.getName();
    }

    return scenarioName;
  }
}
