package org.bdd4j;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * Hooks into the JUnit5 test execution to generate the metadata necessary for BDD4j.
 */
public class BDD4jTestWatcher implements TestWatcher {
  /**
   * {@inheritDoc}
   */
  @Override
  public void testAborted(final ExtensionContext context, final Throwable cause) {
    publishEvent(new ScenarioTestAbortedEvent(
        LocalDateTime.now(),
        context.getTestClass().map(BDD4jTestWatcher::extractFeatureName)
            .orElse(""),
        context.getTestClass().map(BDD4jTestWatcher::extractUserStory)
            .orElse(""),
        context.getTestMethod().map(BDD4jTestWatcher::extractScenarioName)
            .orElse(""),
        context.getTags()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testSuccessful(final ExtensionContext context) {
    publishEvent(new ScenarioTestSuccessfullyCompletedEvent(
        LocalDateTime.now(),
        context.getTestClass().map(BDD4jTestWatcher::extractFeatureName)
            .orElse(""),
        context.getTestClass().map(BDD4jTestWatcher::extractUserStory)
            .orElse(""),
        context.getTestMethod().map(BDD4jTestWatcher::extractScenarioName)
            .orElse(""),
        context.getTags()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testDisabled(final ExtensionContext context, final Optional<String> reason) {
    publishEvent(new ScenarioTestDisabledEvent(
        LocalDateTime.now(),
        context.getTestClass().map(BDD4jTestWatcher::extractFeatureName)
            .orElse(""),
        context.getTestClass().map(BDD4jTestWatcher::extractUserStory)
            .orElse(""),
        context.getTestMethod().map(BDD4jTestWatcher::extractScenarioName)
            .orElse(""),
        context.getTags(),
        reason.orElse("No reason")));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testFailed(final ExtensionContext context, final Throwable cause) {
    publishEvent(new ScenarioTestFailedEvent(
        LocalDateTime.now(),
        context.getTestClass().map(BDD4jTestWatcher::extractFeatureName)
            .orElse(""),
        context.getTestClass().map(BDD4jTestWatcher::extractUserStory)
            .orElse(""),
        context.getTestMethod().map(BDD4jTestWatcher::extractScenarioName)
            .orElse(""),
        context.getTags(),
        cause.getMessage()));
  }

  /**
   * Publishes an event.
   *
   * @param event The event that should be published.
   */
  private static void publishEvent(final ScenarioEvent event) {
    EventBus.getInstance().publish(event);
  }

  /**
   * Extracts the feature name from the given class.
   *
   * @param clazz The class.
   * @return The extracted feature name.
   */
  private static String extractFeatureName(final Class<?> clazz) {
    return Optional.ofNullable(clazz.getAnnotation(Feature.class)).map(Feature::value)
        .orElse("Missing @Feature annotation");
  }

  /**
   * Extracts the user story from the given class.
   *
   * @param clazz The class.
   * @return The extracted user story.
   */
  private static String extractUserStory(final Class<?> clazz) {
    return Optional.ofNullable(clazz.getAnnotation(UserStory.class)).map(UserStory::value)
        .orElse("Missing @UserStory annotation");
  }

  /**
   * Extracts the scenario name from the given method.
   *
   * @param method The method.
   * @return The extracted scenario name.
   */
  private static String extractScenarioName(final Method method) {
    return Optional.ofNullable(method.getAnnotation(Scenario.class)).map(Scenario::value)
        .orElse("Missing @Scenario annotation");
  }
}
