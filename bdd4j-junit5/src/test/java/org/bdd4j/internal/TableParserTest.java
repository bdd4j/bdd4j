package org.bdd4j.internal;

import java.util.Collection;
import org.assertj.core.api.SoftAssertions;
import org.bdd4j.api.DataRow;
import org.junit.jupiter.api.Test;

class TableParserTest {

  @Test
  public void parseTable() {
    final String data = """
            | title                                | author      |
            | The Devil in the White City          | Erik Larson |
            | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
            | In the Garden of Beasts              | Erik Larson |
        """;

    final Collection<DataRow> rows = new TableParser().parseTable(data);

    final SoftAssertions assertions = new SoftAssertions();

    assertions.assertThat(rows).size().isEqualTo(3);
    assertions.assertThat(rows).containsExactly(
        DataRow.builder()
            .with("title", "The Devil in the White City")
            .with("author", "Erik Larson").build(),
        DataRow.builder()
            .with("title", "The Lion, the Witch and the Wardrobe")
            .with("author", "C.S. Lewis").build(),
        DataRow.builder()
            .with("title", "In the Garden of Beasts")
            .with("author", "Erik Larson").build()
    );

    assertions.assertAll();
  }
}