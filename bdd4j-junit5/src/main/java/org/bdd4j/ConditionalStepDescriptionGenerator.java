package org.bdd4j;

import java.util.Objects;

/**
 * A step description generator that substitutes the Given, When, Then keywords with the And keyword
 * when its appropriate.
 */
public class ConditionalStepDescriptionGenerator
{
    private String previousStepType = "";

    /**
     * Generates the full step description for the given step.
     *
     * @param step The step.
     * @param <T>  The type of the test state.
     * @return The step description.
     */
    public <T> String generateStepDescriptionFor(final Step<T> step)
    {
        String currentStepPrefix = step.getClass()
                                       .getSimpleName();

        if (Objects.equals(previousStepType, currentStepPrefix))
        {
            currentStepPrefix = "And";
        }

        previousStepType = step.getClass()
                               .getSimpleName();

        return String.format("%s %s", currentStepPrefix, step.description());
    }
}
