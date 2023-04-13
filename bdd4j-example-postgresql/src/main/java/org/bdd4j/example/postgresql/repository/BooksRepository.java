package org.bdd4j.example.postgresql.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * A repository that can be used to access books.
 */
public class BooksRepository {

  private static final String INSERT_STATEMENT =
      """
          INSERT INTO "books" ("id", "name", "author") VALUES (?,?,?);
          """;

  private static final String SELECT_STATEMENT =
      """
          SELECT "id", "name", "author" from "book" where id = ?
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

  public Optional<BooksRecord> findById(final int id) {
    try {
      final var statement = connection.prepareStatement(SELECT_STATEMENT);

      statement.setInt(1, id);

      final var resultSet = statement.getResultSet();

      if (resultSet.next()) {
        return Optional.of(new BooksRecord(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getInt(3)));
      } else {
        return Optional.empty();
      }
    } catch (final SQLException e) {
      throw new RepositoryException("Failed to find the book", e);
    }
  }
}
