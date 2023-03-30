package org.bdd4j;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * An event that indicates that a scenario test has been successfully completed.
 *
 * @param timestamp    The timestamp that indicates when the event occurred.
 * @param featureName  The name of the feature.
 * @param userStory    The user story of the feature.
 * @param scenarioName The name of the scenario.
 * @param tags         The tags of the scenario.
 */
public record ScenarioTestSuccessfullyCompletedEvent(LocalDateTime timestamp, String featureName,
                                                     String userStory, String scenarioName,
                                                     Collection<String> tags)
        implements ScenarioEvent
{
}
