package org.bdd4j.example.postgresql;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * A provider that can be used to provide the supported postgres versions.
 */
public final class PostgresVersionProvider implements ArgumentsProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
    return Stream.of(Arguments.of("16.0"),
        Arguments.of("15.4"),
        Arguments.of("14.9"),
        Arguments.of("13.12"));
  }
}
