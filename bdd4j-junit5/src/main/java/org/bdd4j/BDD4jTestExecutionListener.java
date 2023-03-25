package org.bdd4j;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

/**
 * A {@link TestExecutionListener} that can be used to subscribe to BDD4j specific events.
 */
public final class BDD4jTestExecutionListener implements TestExecutionListener {
  /**
   * {@inheritDoc}
   */
  @Override
  public void testPlanExecutionStarted(final TestPlan testPlan) {
    reportInfrastructure();
  }

  /**
   * Reports metadata about the infrastructure used to run the tests.
   */
  private void reportInfrastructure() {
    final var entry = BDD4jReportEntry.builder().type(TestEventType.INFRASTRUCTURE_REPORTED)
        .with("hostname", InfrastructureHelper.determineHostname())
        .with("username", InfrastructureHelper.determineUsername())
        .with("operating_system", InfrastructureHelper.determineOperatingSystem())
        .with("cores", String.valueOf(InfrastructureHelper.determineNumberOfCores()))
        .with("java_version", InfrastructureHelper.determineJavaVersion())
        .with("file_encoding", InfrastructureHelper.determineFileEncoding())
        .with("heap_size", String.valueOf(InfrastructureHelper.determineHeapSize())).build();

    reportingEntryPublished(null, ReportEntry.from(entry.asMap()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reportingEntryPublished(final TestIdentifier testIdentifier,
                                      final ReportEntry rawEntry) {
    final BDD4jReportEntry entry =
        BDD4jReportEntry.builder().copy(rawEntry.getKeyValuePairs()).build();

    Logger.getLogger(BDD4jTestExecutionListener.class.getSimpleName())
        .log(Level.INFO, "{0} - {1}", new Object[] {testIdentifier, entry.asMap()});
  }
}
