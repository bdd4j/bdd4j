package org.bdd4j.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bdd4j.api.BDD4jScenario;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.Given;
import org.bdd4j.api.Parameters;
import org.bdd4j.api.Step;
import org.bdd4j.api.Then;
import org.bdd4j.api.When;

/**
 * A parser that can be used to process the contents of a cucumber style feature file and match it
 */
public class FeatureParser {

  private static final Collection<String> KEYWORDS =
      List.of("Feature", "Scenario", "Scenario Outline", "Given", "When", "Then", "And");

  private static final Collection<Class<?>> STEP_RETURN_TYPES =
      List.of(Given.class, When.class, Then.class);

  public <T> FeatureParser(final Collection<Class<? extends BDD4jSteps<?>>> stepCandidates) {
    this.stepCandidates = new ArrayList<>(stepCandidates);
  }

  private final Collection<Class<? extends BDD4jSteps<?>>> stepCandidates;

  /**
   * Parses the content of the given feature.
   *
   * @param feature The feature.
   * @return The parsed scenarios.
   */
  public List<BDD4jScenario<?>> parse(final String feature) {
    final Collection<RequiredStepDefinition> requiredSteps = parseRequiredSteps(feature);
    final List<BDD4jScenario<?>> scenarios = new ArrayList<>();

    for (final Class<? extends BDD4jSteps<?>> stepCandidate : stepCandidates) {
      try {
        final BDD4jSteps<Object> steps =
            (BDD4jSteps<Object>) stepCandidate.getConstructors()[0].newInstance();

        final Collection<Method> stepBuilders = Arrays.stream(stepCandidate.getMethods())
            .filter(method -> STEP_RETURN_TYPES.contains(method.getReturnType()))
            .toList();

        final Collection<Step<Object>> resolvedSteps = new ArrayList<>();

        for (final RequiredStepDefinition requiredStep : requiredSteps) {
          for (final Method method : stepBuilders) {
            if (method.getParameters().length == requiredStep.parameters().size()) {
              final Collection<Object> parameters = new ArrayList<>();
              int i = 0;

              for (final Parameter parameter : method.getParameters()) {
                final Class<?> parameterType = parameter.getType();

                final String storedValue = requiredStep.parameters().get(i);

                if (Integer.class == parameterType) {
                  parameters.add(Integer.valueOf(storedValue));
                } else if (String.class == parameterType) {
                  parameters.add(storedValue);
                } else {
                  throw new IllegalArgumentException(
                      "Failed to handle parameter with type " + parameterType);
                }

                i++;
              }

              final Step<Object> step = (Step<Object>) method.invoke(steps, parameters.toArray());

              final boolean stepMatches = Objects.equals(requiredStep.line(), step.description());

              if (stepMatches) {
                resolvedSteps.add(step);
                break;
              }
            }
          }
        }

        final boolean allStepsHaveBeenResolved = requiredSteps.size() == resolvedSteps.size();

        if (allStepsHaveBeenResolved) {
          scenarios.add(new BDD4jScenario<>(steps,
              new LoggingTestReporter(FeatureParser.class),
              resolvedSteps,
              new Parameters()));
        }
      } catch (final InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }

    return scenarios;
  }

  /**
   * Parses the required steps from the given feature.
   *
   * @param feature The feature that the required steps should be parsed from.
   * @return The required steps.
   */
  private Collection<RequiredStepDefinition> parseRequiredSteps(final String feature) {
    final String[] lines = feature.split("\n");

    String previousKeyword = "";

    final Collection<RequiredStepDefinition> result = new ArrayList<>();

    for (final String line : lines) {
      final String sanitized = line.trim();

      if (isRelevantLine(sanitized)) {
        if (sanitized.startsWith("Given")) {
          previousKeyword = "Given";
          result.add(parseStep(previousKeyword, previousKeyword, sanitized));
        } else if (sanitized.startsWith("When")) {
          previousKeyword = "When";
          result.add(parseStep(previousKeyword, previousKeyword, sanitized));
        } else if (sanitized.startsWith("Then")) {
          previousKeyword = "Then";
          result.add(parseStep(previousKeyword, previousKeyword, sanitized));
        } else if (sanitized.startsWith("And")) {
          // previous keyword stays the same, but the actual keyword is 'And'
          result.add(parseStep(previousKeyword, "And", sanitized));
        } else {
          // process other keywords
        }
      } else if (!sanitized.isEmpty()) {
        throw new IllegalArgumentException("Invalid line detected: " + sanitized);
      }
    }

    return result;
  }

  /**
   * Parses the step from the given line.
   *
   * @param semanticKeyword The semantic keyword
   * @param actualKeyword   The actual keyword present in the line.
   * @param line            The line.
   * @return The required step definition.
   */
  private static RequiredStepDefinition parseStep(final String semanticKeyword,
                                                  final String actualKeyword,
                                                  final String line) {
    return new RequiredStepDefinition(
        semanticKeyword, line.replaceFirst(actualKeyword + " ", ""),
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

  /**
   * Checks whether the line is relevant to the parser.
   *
   * @param line The line.
   * @return True if the line is relevant, otherwise false.
   */
  private static boolean isRelevantLine(final String line) {
    return KEYWORDS.stream().anyMatch(line::startsWith);
  }
}
