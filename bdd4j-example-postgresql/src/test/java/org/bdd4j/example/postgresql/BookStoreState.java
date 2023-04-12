package org.bdd4j.example.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The state used to represent the book stores infrastructure.
 */
public class BookStoreState implements AutoCloseable {

  private final PostgreSQLContainer<?> container;

  public BookStoreState(final PostgreSQLContainer<?> container) {

    this.container = container;
  }

  /**
   * Starts the container used by the tests.
   */
  public void start() {
    container.start();
  }

  /**
   * Applies the schema migrations for the tests.
   */
  public void applySchemaMigrations() {
    final var flyway =
        Flyway.configure()
            .dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
            .load();

    flyway.migrate();
  }

  /**
   * Acquires a new JDBC connection that can be used in the tests.
   *
   * @return The JDBC connection.
   */
  protected Connection acquireJdbcConnection() {
    try {
      return DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(),
          container.getPassword());
    } catch (final SQLException e) {
      throw new RuntimeException("Failed to retrieve jdbc connection", e);
    }
  }

  /**
   * Provides access to an author repository.
   *
   * @return The repository.
   */
  public AuthorsRepository authorRepository() {
    return new AuthorsRepository(acquireJdbcConnection());
  }

  /**
   * Provides access to a book repository.
   *
   * @return The repository.
   */
  public BooksRepository bookRepository() {
    return new BooksRepository(acquireJdbcConnection());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    container.close();
  }
}
