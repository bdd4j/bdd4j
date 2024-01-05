package org.bdd4j.internal;

import java.lang.reflect.Method;
import java.util.Optional;
import org.bdd4j.api.Feature;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;
import org.junit.jupiter.api.DisplayNameGenerator;

/**
 * A {@link org.junit.jupiter.api.DisplayNameGeneration} for BDD4j test cases.
 */
public class BDD4jDisplayNameGenerator implements DisplayNameGenerator {
  /**
   * {@inheritDoc}
   */
  @Override
  public String generateDisplayNameForClass(final Class<?> testClass) {
    return "Feature: " +
        Optional.ofNullable(testClass.getAnnotation(Feature.class)).map(Feature::value)
            .orElse(testClass.getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
    return "Feature: " +
        Optional.ofNullable(nestedClass.getAnnotation(Feature.class)).map(Feature::value)
            .orElse(nestedClass.getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
    return "Scenario: " + extractScenarioName(testMethod);
  }

  /**
   * Extracts the scenario from the given test method.
   *
   * @param testMethod The test method.
   * @return The extracted scenario name.
   */
  private String extractScenarioName(final Method testMethod) {
    if (testMethod.isAnnotationPresent(Scenario.class)) {
      return testMethod.getAnnotation(Scenario.class).value();
    }

    if (testMethod.isAnnotationPresent(ScenarioOutline.class)) {
      return testMethod.getAnnotation(ScenarioOutline.class).description();
    }

    return testMethod.getName();
  }
}
