package org.bdd4j;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An {@link EventListener} that can be used to log {@link ScenarioEvent}s.
 */
public final class LoggingEventListener implements EventListener {
  /**
   * {@inheritDoc}
   */
  @Override
  public void notify(final ScenarioEvent event) {
    Logger.getLogger(LoggingEventListener.class.getSimpleName()).log(Level.INFO, event.toString());
  }
}
