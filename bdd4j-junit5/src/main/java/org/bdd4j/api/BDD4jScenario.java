package org.bdd4j.api;

import static org.bdd4j.internal.BDD4jReportEntry.builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.bdd4j.internal.BDD4jReportEntry;
import org.bdd4j.internal.ConditionalStepDescriptionGenerator;
import org.bdd4j.internal.TestStepVisitor;
import org.junit.jupiter.api.TestReporter;

/**
 * A scenario that defines some steps used to perform a behavior-driven test.
 *
 * <p>This class implements the {@link Runnable} to allow easily executing the scenarios.
 *
 * @param <TS> The type of the test state.
 */
public final class BDD4jScenario<TS> implements Runnable {

  private final BDD4jSteps<TS> stepsWrapper;
  private final TestReporter testReporter;
  private final List<Step<TS>> definedSteps;
  private final Parameters parameters;

  /**
   * Creates a new instance.
   *
   * @param stepsWrapper The steps' wrapper.
   * @param testReporter The test reporter.
   * @param definedSteps The steps defined for the scenario.
   * @param parameters   The parameters for the scenario.
   */
  public BDD4jScenario(final BDD4jSteps<TS> stepsWrapper,
                       final TestReporter testReporter,
                       final Collection<Step<TS>> definedSteps,
                       final Parameters parameters) {

    this.stepsWrapper = stepsWrapper;
    this.testReporter = testReporter;
    this.definedSteps = new ArrayList<>(definedSteps);
    this.parameters = parameters;
  }

  /**
   * Runs the scenario.
   * <p>
   * If the test state implements the {@link AutoCloseable} interface, the
   * {@link AutoCloseable#close()} method will be invoked when the scenario has been completed.
   * This can be used to clean up resources used by the test, such as database connections.
   */
  @Override
  public void run() {
    try (final TestState<TS> state = stepsWrapper.init(parameters)) {
      final var stepVisitor = new TestStepVisitor<>(state);
      final var stepDescriptionGenerator = new ConditionalStepDescriptionGenerator();

      for (final Step<TS> step : definedSteps) {
        final var timestamp = LocalDateTime.now();
        final var fullStepDescription = stepDescriptionGenerator.generateStepDescriptionFor(step);

        try {
          publish(builder().stepExecutionStarted()
              .step(fullStepDescription)
              .build());

          step.accept(stepVisitor);

          publish(builder().stepExecutionCompleted()
              .step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp))
              .build());

        } catch (final AssertionError e) {
          publish(builder().stepExecutionFailed()
              .step(fullStepDescription)
              .executionTime(calculateExecutionTime(timestamp))
              .errorMessage(e.getMessage())
              .build());

          throw e;
        } catch (final Throwable e) {
          publish(builder().stepExecutionFailed()
              .step(fullStepDescription)
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
   * Retrieves the steps for this scenario.
   *
   * @return The steps.
   */
  public List<Step<TS>> steps() {
    return new ArrayList<>(definedSteps);
  }

  /**
   * Retrieves the specified step at the given index from the list of defined steps.
   *
   * @param index The index of the step to retrieve.
   * @return An Optional object containing the step at the specified index, or empty if the index is out of bounds.
   */
  public Optional<Step<TS>> step(final Integer index) {
    return index < steps().size()
        ? Optional.of(definedSteps.get(index))
        : Optional.empty();
  }

  /**
   * Calculates the execution time based on the given timestamp.
   *
   * @param timestamp The timestamp that should be used to calculate the execution time.
   * @return The elapsed time in milliseconds.
   */
  private static long calculateExecutionTime(final LocalDateTime timestamp) {
    return timestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS);
  }

  /**
   * Publishes the given entry to the reporter.
   *
   * @param entry The entry.
   */
  private void publish(final BDD4jReportEntry entry) {
    if (testReporter == null) {
      Logger.getLogger(BDD4jScenario.class.getSimpleName()).warning("The test reporter is null");
    } else {
      testReporter.publishEntry(entry.asMap());
    }
  }

  /**
   * Creates a new {@link BDD4jScenario} with the given test reporter.
   *
   * @param testReporter The test reporter.
   * @return The scenario.
   */
  public BDD4jScenario<TS> withTestReporter(final TestReporter testReporter) {
    return new BDD4jScenario<>(stepsWrapper, testReporter, definedSteps, parameters);
  }
}
