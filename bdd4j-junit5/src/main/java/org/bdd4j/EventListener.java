package org.bdd4j;

import java.util.ServiceLoader;
import java.util.stream.Stream;

/**
 * A listener that can be used to process scenario events.
 */
public interface EventListener {
  /**
   * Notifies the listener with the given event.
   *
   * @param event The event.
   */
  void notify(ScenarioEvent event);

  /**
   * Loads all the listeners.
   *
   * @return The listeners.
   */
  static Stream<EventListener> loadListeners() {
    return ServiceLoader.load(EventListener.class).stream().map(ServiceLoader.Provider::get);
  }
}
