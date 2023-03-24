package org.bdd4j;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

/**
 * A {@link TestExecutionListener} that can be used to subscribe to BDD4j specific events.
 */
public final class BDD4jTestExecutionListener implements TestExecutionListener
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void reportingEntryPublished(final TestIdentifier testIdentifier,
                                      final ReportEntry rawEntry)
  {
    final BDD4jReportEntry entry =
        BDD4jReportEntry.builder().copy(rawEntry.getKeyValuePairs()).build();

    Logger.getLogger(BDD4jTestExecutionListener.class.getSimpleName())
        .log(Level.INFO, "{0} - {1}", new Object[] {testIdentifier, entry});
  }
}
