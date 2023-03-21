package org.bdd4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A runner that can be used to execute BDD scenarios.
 */
public class BDD4jRunner
{
  /**
   * Runs a new scenario.
   * <p>
   * If the test state implements the {@link AutoCloseable} interface, the
   * {@link AutoCloseable#close()} method will be invoked when the scenario has been completed.
   * This can be used to clean up resources used by the test, such as database connections.
   *
   * @param stepsWrapper The steps instance.
   * @param steps        The steps that should be executed.
   * @param <T>          The type of the test state.
   */
  @SafeVarargs
  public static <T> void scenario(final BDD4jSteps<T> stepsWrapper, final Step<T>... steps)
  {
    try (final TestState<T> state = stepsWrapper.init())
    {
      final TestStepVisitor<T> stepVisitor = new TestStepVisitor<>(state);

      publishEvent(new ScenarioTestStartedEvent(LocalDateTime.now(), steps.length,
          "TODO: Determine the actual name of the scenario"));

      String previousStepType = "";

      for (final Step<T> step : steps)
      {
        final var timestamp = LocalDateTime.now();

        String currentStepPrefix = step.getClass().getSimpleName();

        if (Objects.equals(previousStepType, currentStepPrefix))
        {
          currentStepPrefix = "And";
        }

        previousStepType = step.getClass().getSimpleName();

        final var fullStepDescription =
            String.format("%s %s", currentStepPrefix, step.description());

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
    } catch (final Exception e)
    {
      throw new RuntimeException(e);
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
    Logger.getLogger(BDD4jRunner.class.getSimpleName()).log(Level.INFO, event.toString());
  }
}
