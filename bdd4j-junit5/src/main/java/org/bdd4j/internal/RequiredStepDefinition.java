package org.bdd4j.internal;

import java.util.List;

/**
 * A record that represents a required step definition parsed from a feature file.
 *
 * @param keyword    The keyword.
 * @param line       The line.
 * @param parameters The parameters passed to the step.
 */
public record RequiredStepDefinition(String keyword, String line, List<String> parameters) {
}
