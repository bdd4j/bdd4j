package org.bdd4j.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An extractor that can be used to extract a data table from a collection of records.
 */
public final class RecordDataTableExtractor {

  /**
   * Extracts a data table from the given records.
   *
   * @param records The records that the data table should be extracted from.
   * @param <T>     The type of the records.
   * @return The extracted data table.
   */
  public <T> Map<String, List<Object>> extractDataTableFrom(final Collection<T> records) {
    final Map<String, List<Object>> dataTable = new ConcurrentHashMap<>();

    for (final T record : records) {
      if (!record.getClass().isRecord()) {
        throw new IllegalArgumentException("The given type is not a record");
      }

      for (final RecordComponent component : record.getClass().getRecordComponents()) {
        final List<Object> list = dataTable.getOrDefault(component.getName(), new ArrayList<>());

        try {
          list.add(component.getAccessor().invoke(record));
          dataTable.put(component.getName(), list);
        } catch (final IllegalAccessException | InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }

    return dataTable;
  }
}
