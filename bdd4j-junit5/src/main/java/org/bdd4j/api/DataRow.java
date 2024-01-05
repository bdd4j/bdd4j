package org.bdd4j.api;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The representation of a row parsed from a data table.
 */
public class DataRow {
  private final Map<String, Object> data = new ConcurrentHashMap<>();


  /**
   * Creates a new instance.
   *
   * @param data The data.
   */
  public DataRow(final Map<String, Object> data) {
    this.data.putAll(data);
  }

  /**
   * The keys of the row.
   *
   * @return The keys.
   */
  public Collection<String> keys() {
    return data.keySet();
  }

  /**
   * Retrieves the string from the given key.
   *
   * @param key The key.
   * @return The string.
   */
  public String getString(final String key) {
    return (String) data.get(key);
  }

  /**
   * Retrieves an integer from the given key.
   *
   * @param key The key.
   * @return The integer value.
   */
  public Integer getInteger(final String key) {
    return Integer.valueOf(getString(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final DataRow dataRow = (DataRow) o;
    return Objects.equals(data, dataRow.data);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(data);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return data.toString();
  }

  /**
   * Creates a new builder for a {@link DataRow}
   *
   * @return The builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * A builder for {@link DataRow} instances.
   */
  public static class Builder {

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    /**
     * Adds the given key and value pair to the data.
     *
     * @param key   The key.
     * @param value The value.
     * @return The builder.
     */
    public Builder with(final String key, final String value) {
      data.put(key, value);
      return this;
    }

    /**
     * Builds the {@link DataRow}
     *
     * @return The row.
     */
    public DataRow build() {
      return new DataRow(data);
    }
  }
}
