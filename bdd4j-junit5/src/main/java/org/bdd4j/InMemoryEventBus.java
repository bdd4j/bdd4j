package org.bdd4j;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An in-memory implementation of the {@link EventBus} interface.
 */
public final class InMemoryEventBus implements EventBus
{
  private static final Collection<EventListener> SUBSCRIBERS = new ArrayList<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void publish(final ScenarioEvent event)
  {
    SUBSCRIBERS.forEach(subscriber -> subscriber.notify(event));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void subscribe(final EventListener subscriber)
  {
    SUBSCRIBERS.add(subscriber);
  }
}
