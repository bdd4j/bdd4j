package org.bdd4j.example.postgresql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A repository that can be used to access books.
 */
public class BooksRepository {

  private static final String INSERT_STATEMENT =
      """
          INSERT INTO "books" ("id", "name", "author") VALUES (?,?,?);
          """;


  private final Connection connection;

  /**
   * Creates a new instance.
   *
   * @param connection The database connection.
   */
  public BooksRepository(final Connection connection) {
    this.connection = connection;
  }

  /**
   * Creates a new book.
   *
   * @param book The books' data.
   */
  public void create(final BooksRecord book) {
    try {
      final var statement = connection.prepareStatement(INSERT_STATEMENT);

      statement.setInt(1, book.id());
      statement.setString(2, book.name());
      statement.setInt(3, book.author());

      statement.executeUpdate();
    } catch (final SQLException e) {
      throw new RepositoryException("Failed to create the book", e);
    }
  }
}
