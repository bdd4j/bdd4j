package org.bdd4j.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bdd4j.api.DataRow;

/**
 * A parser that can be used to parse cucumber data tables.
 */
public class TableParser {

  /**
   * Parses the table from the given string.
   *
   * @param data The data that the table should be parsed from.
   * @return The row parsed from the table.
   */
  public Collection<DataRow> parseTable(final String data) {
    final Collection<DataRow> result = new ArrayList<>();
    final List<String> keys = new ArrayList<>();
    boolean firstLine = true;

    for (final String line : data.split("\n")) {
      if (firstLine) {
        for (final String token : line.split("\\|")) {
          if (token.trim().isEmpty()) {
            continue;
          }
          keys.add(token.trim());
        }

        firstLine = false;
      } else {
        int index = 0;

        final Map<String, Object> rowData = new ConcurrentHashMap<>();

        for (final String token : line.split("\\|")) {
          if (index == 0 && token.trim().isEmpty()) {
            continue;
          }

          rowData.put(keys.get(index), token.trim());
          index++;
        }

        result.add(new DataRow(rowData));
      }
    }

    return result;
  }
}
