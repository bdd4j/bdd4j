package org.bdd4j.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser that can be used to parse cucumber style steps.
 */
public class StepParser {

  /**
   * Parses the step from the given line.
   *
   * @param semanticKeyword The semantic keyword
   * @param actualKeyword   The actual keyword present in the line.
   * @param line            The line.
   * @return The required step definition.
   */
  public static RequiredStepDefinition parseStep(final String semanticKeyword,
                                                 final String actualKeyword,
                                                 final String line) {
    return new RequiredStepDefinition(
        semanticKeyword,
        line.replaceFirst(actualKeyword + " ", ""),
        parseParameters(line));
  }

  /**
   * Parses the parameters from the given line.
   *
   * @param line The line that the parameters should be parsed from.
   * @return The list of string parameters.
   */
  private static List<String> parseParameters(final String line) {
    final Pattern pattern = Pattern.compile("'(.*?[^\\\\])'");
    final Matcher matcher = pattern.matcher(line);

    final List<String> parameters = new ArrayList<>();

    while (matcher.find()) {
      parameters.add(matcher.group(1));
    }

    return parameters;
  }
}
