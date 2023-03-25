package org.bdd4j;

import static org.bdd4j.BDD4jReportEntry.builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestReporter;

/**
 * An abstract base class that can be used to implement BDD4j scenario tests with.
 * <p>
 * The class provides the main entry point for scenario tests in the form of the
 * {@link AbstractScenarioTest#scenario(BDD4jSteps, Step[])} method.
 * <p>
 * The tests progress is reported to the {@link TestReporter}.
 */
public abstract class AbstractScenarioTest {
  protected TestReporter reporter;

  @BeforeEach
  public void setupReporter(final TestReporter reporter) {
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
  protected final <T> void scenario(final BDD4jSteps<T> stepsWrapper, final Step<T>... steps) {
    try (final TestState<T> state = stepsWrapper.init()) {
      final var stepVisitor = new TestStepVisitor<>(state);
      final var stepDescriptionGenerator = new ConditionalStepDescriptionGenerator();

      for (final Step<T> step : steps) {
        final var timestamp = LocalDateTime.now();
        final var fullStepDescription = stepDescriptionGenerator.generateStepDescriptionFor(step);

        try {
          publish(builder().stepExecutionStarted().step(fullStepDescription).build());

          step.accept(stepVisitor);

          publish(builder().stepExecutionCompleted().step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp))
              .build());

        } catch (final AssertionError e) {
          publish(builder().stepExecutionFailed().step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp))
              .errorMessage(e.getMessage())
              .build());

          throw e;
        } catch (final Throwable e) {
          publish(builder().stepExecutionFailed().step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp))
              .errorMessage(e.getMessage())
              .build());

          throw new AssertionError("Failed to execute the scenario", e);
        }
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Calculates the execution time based on the given timestamp.
   *
   * @param timestamp The timestamp that should be used to calculate the execution time.
   * @return The elapsed time in milliseconds.
   */
  private static long calculateExecutionTime(LocalDateTime timestamp) {
    return timestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS);
  }

  /**
   * Publishes the given entry to the reporter.
   *
   * @param entry The entry.
   */
  private void publish(final BDD4jReportEntry entry) {
    reporter.publishEntry(entry.asMap());
  }
}
