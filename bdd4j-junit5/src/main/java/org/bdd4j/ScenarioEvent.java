package org.bdd4j;

import java.time.LocalDateTime;

/**
 * An event that occurs while executing the test scenario.
 */
public interface ScenarioEvent {
  /**
   * The timestamp that indicates when the event has occurred.
   *
   * @return The timestamp.
   */
  LocalDateTime timestamp();
}
