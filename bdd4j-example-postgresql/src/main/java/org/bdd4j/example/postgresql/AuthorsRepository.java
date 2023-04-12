package org.bdd4j.example.postgresql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A repository that can be used to access authors.
 */
public final class AuthorsRepository {

  private static final String INSERT_STATEMENT =
      """
          INSERT INTO "authors" ("id", "name") VALUES (?,?);
          """;

  private final Connection connection;

  /**
   * Creates a new instance.
   *
   * @param connection The database connection.
   */
  public AuthorsRepository(final Connection connection) {
    this.connection = connection;
  }

  /**
   * Creates a new author.
   *
   * @param author The authors data.
   */
  public void create(final AuthorRecord author) {
    try {
      final var statement = connection.prepareStatement(INSERT_STATEMENT);

      statement.setInt(1, author.id());
      statement.setString(2, author.name());

      statement.executeUpdate();
    } catch (final SQLException e) {
      throw new RepositoryException("Failed to create the author", e);
    }
  }
}
