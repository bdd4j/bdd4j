package org.bdd4j;

import java.util.Objects;

/**
 * A step description generator that substitutes the Given, When, Then keywords with the And keyword
 * when it's appropriate.
 */
public final class ConditionalStepDescriptionGenerator {
  private String previousStepKeyword = "";

  /**
   * Generates the full step description for the given step.
   *
   * @param step The step.
   * @param <T>  The type of the test state.
   * @return The step description.
   */
  public <T> String generateStepDescriptionFor(final Step<T> step) {
    String currentStepKeyword = step.getClass().getSimpleName();

    if (Objects.equals(previousStepKeyword, currentStepKeyword)) {
      currentStepKeyword = "And";
    }

    previousStepKeyword = step.getClass().getSimpleName();

    return String.format("%s %s", currentStepKeyword, step.description());
  }
}
