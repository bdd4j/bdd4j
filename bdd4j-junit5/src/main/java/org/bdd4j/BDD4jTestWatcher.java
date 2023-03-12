package org.bdd4j;

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
  public void testAborted(final ExtensionContext context,
                          final Throwable cause)
  {
    TestWatcher.super.testAborted(context, cause);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testSuccessful(final ExtensionContext context)
  {
    context.getTestClass()
        .filter(clazz -> clazz.isAnnotationPresent(BDD4jTest.class))
        .ifPresent(clazz -> {
          context.publishReportEntry("feature",
              Optional.ofNullable(clazz.getAnnotation(Feature.class))
                  .map(Feature::value)
                  .orElse(""));

          context.publishReportEntry("userStory",
              Optional.ofNullable(clazz.getAnnotation(UserStory.class))
                  .map(UserStory::value)
                  .orElse(""));
        });

    context.getTestMethod()
        .filter(method -> method.isAnnotationPresent(Scenario.class))
        .ifPresent(method -> {
          context.publishReportEntry("scenario",
              Optional.ofNullable(method.getAnnotation(Scenario.class))
                  .map(Scenario::value)
                  .orElse(""));
        });

    System.out.println(context.getTags());
    System.out.println(context.getTestClass());
    System.out.println(context);
    TestWatcher.super.testSuccessful(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testDisabled(final ExtensionContext context,
                           final Optional<String> reason)
  {
    TestWatcher.super.testDisabled(context, reason);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testFailed(final ExtensionContext context,
                         final Throwable cause)
  {
    TestWatcher.super.testFailed(context, cause);
  }
}
