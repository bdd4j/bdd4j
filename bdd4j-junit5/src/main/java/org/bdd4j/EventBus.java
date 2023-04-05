package org.bdd4j;

import java.util.ServiceLoader;

/**
 * The common interface for objects that represent an event bus.
 */
public interface EventBus {
  /**
   * Publishes the given event.
   *
   * @param event The event.
   */
  void publish(final ScenarioEvent event);

  /**
   * Subscribes the given subscriber.
   *
   * @param subscriber The subscriber.
   */
  void subscribe(final EventListener subscriber);

  /**
   * Retrieves the default event bus.
   *
   * @return The event bus.
   */
  static EventBus getInstance() {
    return ServiceLoader.load(EventBus.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default event bus provided"));
  }
}
