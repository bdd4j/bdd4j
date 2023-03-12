package org.bdd4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A runner that can be used to execute BDD scenarios.
 */
public class BDD4jRunner
{
  /**
   * Creates a new scenario.
   *
   * @param stepsWrapper The steps instance.
   * @param steps        The steps that should be executed.
   * @param <T>          The type of the test state.
   */
  @SafeVarargs
  public static <T> void scenario(final BDD4jSteps<T> stepsWrapper, final Step<T>... steps)
  {
    final TestStepVisitor<T> stepVisitor = new TestStepVisitor<>(stepsWrapper.init());

    publishEvent(new ScenarioTestStartedEvent(LocalDateTime.now(), steps.length,
        "TODO: Determine the actual name of the scenario"));

    for (final Step<T> step : steps)
    {
      final var timestamp = LocalDateTime.now();

      final var fullStepDescription = step.getClass().getSimpleName() + " " + step.description();

      try
      {
        publishEvent(new StepExecutionStartedEvent(LocalDateTime.now(), fullStepDescription));

        step.accept(stepVisitor);

        publishEvent(new StepExecutionCompletedEvent(LocalDateTime.now(), fullStepDescription,
            calculateExecutionTime(timestamp)));

      } catch (final AssertionError e)
      {
        publishEvent(
            new StepExecutionFailedEvent(LocalDateTime.now(), fullStepDescription, e.getMessage(),
                calculateExecutionTime(timestamp)));

        throw e;
      } catch (final Throwable e)
      {
        publishEvent(
            new StepExecutionFailedEvent(LocalDateTime.now(), fullStepDescription, e.getMessage(),
                calculateExecutionTime(timestamp)));

        throw new AssertionError("Failed to execute the scenario", e);
      }
    }
  }

  /**
   * Calculates the execution time based on the given timestamp.
   *
   * @param timestamp The timestamp that should be used to calculate the execution time.
   * @return The elapsed time in milliseconds.
   */
  private static long calculateExecutionTime(LocalDateTime timestamp)
  {
    return timestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS);
  }

  /**
   * Publishes an event.
   *
   * @param event The event that should be published.
   */
  private static void publishEvent(final ScenarioEvent event)
  {
    //TODO: Support some kind of event bus, that can be subscribed to by various consumers

    System.out.println(event);
  }
}
