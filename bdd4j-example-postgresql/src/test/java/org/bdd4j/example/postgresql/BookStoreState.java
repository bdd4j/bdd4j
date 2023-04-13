package org.bdd4j.example.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bdd4j.example.postgresql.repository.AuthorsRepository;
import org.bdd4j.example.postgresql.repository.BooksRepository;
import org.bdd4j.example.postgresql.service.AuthorService;
import org.bdd4j.example.postgresql.service.BookService;
import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The state used to represent the book stores infrastructure.
 */
public final class BookStoreState implements AutoCloseable {

  private final PostgreSQLContainer<?> container;

  /**
   * Creates a new instance.
   *
   * @param container The container that should be used by this instance.
   */
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
  private Connection acquireJdbcConnection() {
    try {
      return DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(),
          container.getPassword());
    } catch (final SQLException e) {
      throw new RuntimeException("Failed to retrieve jdbc connection", e);
    }
  }

  /**
   * Creates a new book service.
   *
   * @return The book service.
   */
  public BookService createBookService() {
    return new BookService(createBookRepository(), createAuthorService());
  }

  /**
   * Creates a new author service.
   *
   * @return The author service.
   */
  public AuthorService createAuthorService() {
    return new AuthorService(createAuthorRepository());
  }

  /**
   * Provides access to an author repository.
   *
   * @return The repository.
   */
  private AuthorsRepository createAuthorRepository() {
    return new AuthorsRepository(acquireJdbcConnection());
  }

  /**
   * Provides access to a book repository.
   *
   * @return The repository.
   */
  private BooksRepository createBookRepository() {
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
