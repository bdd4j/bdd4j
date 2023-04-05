package org.bdd4j;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that the step execution has started.
 *
 * @param timestamp The timestamp that indicates when the event occurred.
 * @param step      The textual description of the step.
 */
public record StepExecutionStartedEvent(LocalDateTime timestamp,
                                        String step) implements StepExecutionEvent {
}
