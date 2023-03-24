package org.bdd4j;

import org.junit.jupiter.api.TestReporter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.bdd4j.BDD4jReportEntry.builder;

/**
 * A runner that can be used to execute BDD scenarios.
 */
public class BDD4jRunner
{
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
     * Publishes the given entry to the reporter.
     *
     * @param entry The entry.
     */
    private static void publish(TestReporter reporter, final BDD4jReportEntry entry)
    {
        reporter.publishEntry(entry.asMap());
    }

    /**
     * Publishes an event.
     *
     * @param event The event that should be published.
     */
    private static void publishEvent(final ScenarioEvent event)
    {
        EventBus.getInstance()
                .publish(event);
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
    public static <T> void scenario(final BDD4jSteps<T> stepsWrapper, TestReporter testReporter, final Step<T>... steps)
    {
        try (final TestState<T> state = stepsWrapper.init())
        {
            final var stepVisitor = new TestStepVisitor<>(state);
            final var stepDescriptionGenerator = new ConditionalStepDescriptionGenerator();

            for (final Step<T> step : steps)
            {
                final var timestamp = LocalDateTime.now();
                final var fullStepDescription = stepDescriptionGenerator.generateStepDescriptionFor(step);

                try
                {
                    publish(testReporter, builder().stepExecutionStarted()
                                                   .step(fullStepDescription)
                                                   .build());

                    step.accept(stepVisitor);

                    publish(testReporter, builder().stepExecutionCompleted()
                                                   .step(fullStepDescription)
                                                   .executionTime(calculateExecutionTime(timestamp))
                                                   .build());

                } catch (final AssertionError e)
                {
                    publish(testReporter, builder().stepExecutionFailed()
                                                   .step(fullStepDescription)
                                                   .executionTime(calculateExecutionTime(timestamp))
                                                   .errorMessage(e.getMessage())
                                                   .build());

                    throw e;
                } catch (final Throwable e)
                {
                    publish(testReporter, builder().stepExecutionFailed()
                                                   .step(fullStepDescription)
                                                   .executionTime(calculateExecutionTime(timestamp))
                                                   .errorMessage(e.getMessage())
                                                   .build());

                    throw new AssertionError("Failed to execute the scenario", e);
                }
            }
        } catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
