package org.bdd4j;

import java.time.LocalDateTime;

/**
 * An event that can be used to represent a completed step execution.
 *
 * @param timestamp             The timestamp that indicates when the event occurred.
 * @param step                  The textual description of the step.
 * @param executionTimeInMillis The elapsed execution time in milliseconds.
 */
public record StepExecutionCompletedEvent(LocalDateTime timestamp,
                                          String step,
                                          Long executionTimeInMillis)
    implements StepExecutionEvent {
}
