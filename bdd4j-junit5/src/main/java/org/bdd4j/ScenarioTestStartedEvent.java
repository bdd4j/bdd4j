package org.bdd4j;

import java.time.LocalDateTime;

/**
 * An event that indicates that the execution of a scenario test started.
 *
 * @param timestamp          The timestamp.
 * @param totalNumberOfSteps The total number of steps for this scenario.
 * @param scenario           The name of the executed scenario.
 */
public record ScenarioTestStartedEvent(LocalDateTime timestamp, int totalNumberOfSteps,
                                       String scenario) implements ScenarioEvent
{
}
