package org.bdd4j;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * Hooks into the JUnit5 test execution to generate the metadata necessary for BDD4j.
 */
public class BDD4jTestWatcher implements TestWatcher
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void testAborted(final ExtensionContext context, final Throwable cause)
  {
    System.out.println(new ScenarioTestAbortedEvent(
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
  public void testSuccessful(final ExtensionContext context)
  {
    System.out.println(new ScenarioTestSuccessfullyCompletedEvent(
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
  public void testDisabled(final ExtensionContext context, final Optional<String> reason)
  {
    System.out.println(new ScenarioTestDisabledEvent(
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
  public void testFailed(final ExtensionContext context, final Throwable cause)
  {
    System.out.println(new ScenarioTestFailedEvent(
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
   * Extracts the feature name from the given class.
   *
   * @param clazz The class.
   * @return The extracted feature name.
   */
  private static String extractFeatureName(final Class<?> clazz)
  {
    return Optional.ofNullable(clazz.getAnnotation(Feature.class)).map(Feature::value)
        .orElse("Missing @Feature annotation");
  }

  /**
   * Extracts the user story from the given class.
   *
   * @param clazz The class.
   * @return The extracted user story.
   */
  private static String extractUserStory(final Class<?> clazz)
  {
    return Optional.ofNullable(clazz.getAnnotation(UserStory.class)).map(UserStory::value)
        .orElse("Missing @UserStory annotation");
  }

  /**
   * Extracts the scenario name from the given method.
   *
   * @param method The method.
   * @return The extracted scenario name.
   */
  private static String extractScenarioName(final Method method)
  {
    return Optional.ofNullable(method.getAnnotation(Scenario.class)).map(Scenario::value)
        .orElse("Missing @Scenario annotation");
  }
}
