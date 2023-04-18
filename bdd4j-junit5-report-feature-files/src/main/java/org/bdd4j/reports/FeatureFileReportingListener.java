package org.bdd4j.reports;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.bdd4j.BDD4jReportEntry;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.listeners.OutputDir;

/**
 * A {@link TestExecutionListener} that listens to the events of the executed scenarios and
 * reports them in the format of a .feature file.
 *
 * <p>The directory to which the reports will be written to can be specified by setting the
 * <code>bdd4j.platform.reporting.output.dir</code> property.
 *
 * <p>By default, the files will be written to the gradle build directory.
 */
public final class FeatureFileReportingListener implements TestExecutionListener {

  private static final String OUTPUT_DIR_PROPERTY_NAME = "bdd4j.platform.reporting.output.dir";

  private final Map<String, StringBuilder> featureBuilders = new ConcurrentHashMap<>();

  private final Map<TestIdentifier, StringBuilder> scenarioBuilders = new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void reportingEntryPublished(final TestIdentifier testIdentifier,
                                      final ReportEntry entry) {
    final var parsed = BDD4jReportEntry.builder().copy(entry.getKeyValuePairs()).build();

    if (parsed.type().isPresent()) {
      switch (parsed.type().get()) {
        case FEATURE_METADATA_REPORTED -> reportFeatureMetadata(testIdentifier, parsed);
        case SCENARIO_METADATA_REPORTED -> reportScenarioMetadata(testIdentifier, parsed);
        case STEP_EXECUTION_STARTED -> reportStep(testIdentifier, parsed);
      }
    }
  }

  /**
   * Reports the step information.
   *
   * @param testIdentifier The test identifier.
   * @param entry          The entry.
   */
  private void reportStep(final TestIdentifier testIdentifier,
                          final BDD4jReportEntry entry) {
    appendToScenario(testIdentifier, entry.step().map(step -> "    " + step).orElse(""));
  }

  /**
   * Reports the scenario metadata.
   *
   * @param testIdentifier The test identifier.
   * @param entry          The entry.
   */
  private void reportScenarioMetadata(final TestIdentifier testIdentifier,
                                      final BDD4jReportEntry entry) {
    appendToScenario(testIdentifier,
        "  Scenario: " + entry.asMap().getOrDefault("scenario", "") + "\n");
  }

  /**
   * Reports the feature metadata.
   *
   * @param testIdentifier The test identifier.
   * @param entry          The entry.
   */
  private void reportFeatureMetadata(final TestIdentifier testIdentifier,
                                     final BDD4jReportEntry entry) {
    if (featureMetadataHasNotBeenReported(testIdentifier)) {
      appendToFeature(testIdentifier, "Feature: "
          + entry.asMap().getOrDefault("feature", ""));

      appendToFeature(testIdentifier, "");

      for (final String line :
          entry.asMap().getOrDefault("story", "").split("\n")) {
        appendToFeature(testIdentifier, "  " + line);
      }

      appendToFeature(testIdentifier, "");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void testPlanExecutionFinished(final TestPlan testPlan) {
    for (final Map.Entry<TestIdentifier, StringBuilder> entry : scenarioBuilders.entrySet()) {
      appendToFeature(entry.getKey(), entry.getValue().toString());
    }

    for (final Map.Entry<String, StringBuilder> entry : featureBuilders.entrySet()) {
      final Path featureFile =
          OutputDir.create(testPlan.getConfigurationParameters().get(OUTPUT_DIR_PROPERTY_NAME))
              .createFile(entry.getKey(), "feature");

      try {
        Files.writeString(featureFile, entry.getValue());
      } catch (final Exception e) {
        throw new JUnitException("Failed to write event file: " + featureFile, e);
      }
    }
  }

  /**
   * Extracts the class name from the given identifier.
   *
   * @param identifier The identifier.
   * @return The extracted class name.
   */
  private String extractClassNameFrom(final TestIdentifier identifier) {
    return identifier.getUniqueIdObject()
        .getSegments()
        .stream()
        .filter(segment -> Objects.equals("class", segment.getType()))
        .map(UniqueId.Segment::getValue).findFirst()
        .orElseThrow(() -> new RuntimeException("Failed to extract class name from " + identifier));
  }

  /**
   * Checks whether the metadata has not been reported for the given test identifier.
   *
   * @param identifier The identifier.
   * @return True if the metadata has not been reported, otherwise false.
   */
  private boolean featureMetadataHasNotBeenReported(final TestIdentifier identifier) {
    return !featureBuilders.containsKey(extractClassNameFrom(identifier));
  }

  /**
   * Appends the given line to the scenario buffer.
   *
   * @param identifier The identifier.
   * @param line       The line.
   */
  private void appendToScenario(final TestIdentifier identifier,
                                final String line) {
    final var builder = scenarioBuilders.getOrDefault(identifier, new StringBuilder());

    builder.append(line).append("\n");

    scenarioBuilders.put(identifier, builder);
  }

  /**
   * Appends the given line to the feature buffer.
   *
   * @param identifier The identifier.
   * @param line       The line.
   */
  private void appendToFeature(final TestIdentifier identifier,
                               final String line) {
    final String featureIdentifier = extractClassNameFrom(identifier);

    final var builder =
        featureBuilders.getOrDefault(featureIdentifier, new StringBuilder());

    builder.append(line).append("\n");

    featureBuilders.put(featureIdentifier, builder);
  }
}
