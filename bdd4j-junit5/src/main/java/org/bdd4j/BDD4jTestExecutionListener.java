package org.bdd4j;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

public class BDD4jTestExecutionListener implements TestExecutionListener
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void reportingEntryPublished(final TestIdentifier testIdentifier,
                                      final ReportEntry entry)
  {
    Logger.getLogger(BDD4jTestExecutionListener.class.getSimpleName())
        .log(Level.INFO, "{0} - {1}", new Object[] {testIdentifier, entry});
  }
}
