package org.bdd4j.api;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that can be used to represent parameters for a test run.
 */
public final class Parameters {

  private final Map<String, Object> values = new ConcurrentHashMap<>();

  /**
   * Sets the string for the given key.
   *
   * @param key   The key that should be set.
   * @param value The value that should be set.
   */
  public void set(final String key, String value) {
    values.put(key, value);
  }

  /**
   * Retrieves the string stored for the given key.
   *
   * @param key The key that should be retrieved.
   * @return The stored value.
   */
  public String getString(final String key) {
    Object value = values.get(key);

    if (value instanceof String) {
      return (String) value;
    }

    throw new IllegalArgumentException(
        MessageFormat.format("The stored value for key {0} is not a string",
            key));
  }
}
