package org.bdd4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestReporter;

public class AbstractScenarioTest
{
  protected TestReporter reporter;

  @BeforeEach
  public void setupReporter(final TestReporter reporter)
  {
    this.reporter = reporter;
  }

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
  protected final <T> void scenario(final BDD4jSteps<T> stepsWrapper, final Step<T>... steps)
  {
    try (final TestState<T> state = stepsWrapper.init())
    {
      final TestStepVisitor<T> stepVisitor = new TestStepVisitor<>(state);

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
          reporter.publishEntry(
              newEntry().type("STEP_EXECUTION_STARTED").step(fullStepDescription).build());

          step.accept(stepVisitor);

          reporter.publishEntry(
              newEntry().type("STEP_EXECUTION_COMPLETED").step(fullStepDescription)
                  .executionTime(calculateExecutionTime(timestamp)).build());
        } catch (final AssertionError e)
        {
          reporter.publishEntry(newEntry().type("STEP_EXECUTION_FAILED").step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp)).errorMessage(e.getMessage())
              .build());

          throw e;
        } catch (final Throwable e)
        {
          reporter.publishEntry(newEntry().type("STEP_EXECUTION_FAILED").step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp)).errorMessage(e.getMessage())
              .build());

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
   * Creates a builder for a new entry.
   *
   * @return The builder.
   */
  public static EntryBuilder newEntry()
  {
    return new EntryBuilder();
  }

  /**
   * A builder that can be used to build entries for the {@link TestReporter}
   */
  private static class EntryBuilder
  {
    private final Map<String, String> entry = new ConcurrentHashMap<>();

    /**
     * Adds a new property to the entry.
     *
     * @param key   The key.
     * @param value The value.
     * @return The builder.
     */
    public EntryBuilder with(final String key, final String value)
    {
      entry.put(key, value);
      return this;
    }

    /**
     * Sets the step description.
     *
     * @param step The step.
     * @return The builder.
     */
    public EntryBuilder step(final String step)
    {
      return with("step", step);
    }

    /**
     * Sets the type of the entry.
     *
     * @param type The type.
     * @return The builder.
     */
    public EntryBuilder type(final String type)
    {
      return with("type", type);
    }

    /**
     * Sets the error message to the entry.
     *
     * @param errorMessage The error message.
     * @return The builder.
     */
    public EntryBuilder errorMessage(final String errorMessage)
    {
      return with("error_message", errorMessage);
    }

    /**
     * Sets the execution time.
     *
     * @param executionTimeInMs The execution time in milliseconds.
     * @return The builder.
     */
    public EntryBuilder executionTime(final Long executionTimeInMs)
    {
      return with("execution_time_in_ms", String.valueOf(executionTimeInMs));
    }

    /**
     * Builds the entry.
     *
     * @return The entry.
     */
    public Map<String, String> build()
    {
      return new ConcurrentHashMap<>(entry);
    }
  }
}
