package org.bdd4j.internal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class of utilities used to parse scenarios.
 */
public final class ScenarioParserUtil {

  /**
   * Private constructor to prevent instantiation.
   */
  private ScenarioParserUtil() {
  }

  /**
   * Splits the given feature into the contained scenario and scenario outline definitions.
   *
   * @param feature The feature that should be split.
   * @return The split scenarios.
   */
  public static Collection<String> splitScenarios(final String feature) {
    final Collection<String> scenarios = new ArrayList<>();

    StringBuilder builder = new StringBuilder();

    boolean isFirst = true;

    for (final String line : feature.split("\n")) {
      final String trimmedLine = line.trim();

      if (trimmedLine.startsWith(CucumberKeywords.SCENARIO.concat(": ")) ||
          trimmedLine.startsWith(CucumberKeywords.SCENARIO_OUTLINE.concat(": "))) {
        //Flush previous scenario
        if (!isFirst) {
          scenarios.add(builder.toString());
          builder = new StringBuilder();
        }
        isFirst = false;
      }

      if (!trimmedLine.startsWith(CucumberKeywords.FEATURE) && !trimmedLine.isEmpty()) {
        builder.append(trimmedLine).append('\n');
      }
    }

    scenarios.add(builder.toString());

    return scenarios;
  }
}
