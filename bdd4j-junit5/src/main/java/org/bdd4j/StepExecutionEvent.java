package org.bdd4j;

import java.time.LocalDateTime;

/**
 * The common interface for objects that represent a step execution event.
 */
public interface StepExecutionEvent
{
  /**
   * The timestamp that indicates when the event occurred.
   *
   * @return The timestamp.
   */
  LocalDateTime timestamp();

  /**
   * The textual description of the step.
   *
   * @return The textual description of the step.
   */
  String step();
}
