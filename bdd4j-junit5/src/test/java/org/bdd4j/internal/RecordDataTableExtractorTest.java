package org.bdd4j.internal;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class RecordDataTableExtractorTest {

  @Test
  public void extractDataFromRecord() {
    final var extractor = new RecordDataTableExtractor();

    final var dataTable =
        extractor.extractDataTableFrom(List.of(new Data("John", 42)));

    final var assertions = new SoftAssertions();

    assertions.assertThat(dataTable.get("name")).isEqualTo(List.of("John"));
    assertions.assertThat(dataTable.get("age")).isEqualTo(List.of(42));

    assertions.assertAll();
  }

  public record Data(String name, Integer age) {
  }
}