package org.bdd4j.internal;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.logging.Logger;
import org.junit.jupiter.api.TestReporter;

/**
 * A {@link TestReporter} that writes all published entries a {@link Logger}.
 */
public class LoggingTestReporter implements TestReporter {

  private final Logger logger;

  /**
   * Creates a new instance.
   *
   * @param testClass The test class.
   */
  public LoggingTestReporter(final Class<?> testClass) {
    requireNonNull(testClass, "The test class may not be null");
    this.logger = Logger.getLogger(testClass.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void publishEntry(final Map<String, String> map) {
    logger.info(map.toString());
  }
}
