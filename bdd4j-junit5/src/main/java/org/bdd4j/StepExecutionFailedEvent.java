package org.bdd4j;

import java.time.LocalDateTime;

/**
 * An event that can be used to represent a failed step execution.
 *
 * @param timestamp             The timestamp that indicates when the event occurred.
 * @param step                  The textual description of the step.
 * @param errorMessage          The error message that describes the failure.
 * @param executionTimeInMillis The elapsed execution time in milliseconds.
 */
public record StepExecutionFailedEvent(LocalDateTime timestamp,
                                       String step,
                                       String errorMessage,
                                       Long executionTimeInMillis) implements StepExecutionEvent {
}
