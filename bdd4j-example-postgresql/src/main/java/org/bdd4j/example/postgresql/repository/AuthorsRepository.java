package org.bdd4j.example.postgresql.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * A repository that can be used to access authors.
 */
public final class AuthorsRepository {

  private static final String INSERT_STATEMENT =
      """
          INSERT INTO "authors" ("id", "name") VALUES (?,?);
          """;

  private static final String SELECT_STATEMENT =
      """
          SELECT "id", "name" from "authors" where id = ?
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
   * @param data The authors' data.
   */
  public void create(final AuthorRecord data) {
    try {
      final var statement = connection.prepareStatement(INSERT_STATEMENT);

      statement.setInt(1, data.id());
      statement.setString(2, data.name());

      statement.executeUpdate();
    } catch (final SQLException e) {
      throw new RepositoryException("Failed to create the author", e);
    }
  }

  /**
   * Retrieves a specific author by its ID.
   *
   * @param id The ID of the author.
   * @return The authors record, otherwise {@link Optional#empty()}.
   */
  public Optional<AuthorRecord> getById(final Integer id) {
    try {
      final var statement = connection.prepareStatement(SELECT_STATEMENT);

      statement.setInt(1, id);

      final var resultSet = statement.executeQuery();

      if (resultSet == null) {
        return Optional.empty();
      } else if (resultSet.next()) {
        return Optional.of(new AuthorRecord(
            resultSet.getInt(1),
            resultSet.getString(2)));
      } else {
        return Optional.empty();
      }
    } catch (final SQLException e) {
      throw new RepositoryException("Failed to retrieve the author", e);
    }
  }
}
