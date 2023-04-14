package org.bdd4j.example.postgresql.service;

import java.util.Optional;
import org.bdd4j.example.postgresql.repository.AuthorRecord;
import org.bdd4j.example.postgresql.repository.AuthorsRepository;

/**
 * A service that can be used to interact with authors.
 */
public final class AuthorService {

  private final AuthorsRepository repository;

  /**
   * Creates a new instance.
   *
   * @param repository The repository that should be used by this instance.
   */
  public AuthorService(final AuthorsRepository repository) {
    this.repository = repository;
  }

  /**
   * Retrieves a specific author by its ID.
   *
   * @param id The ID of the author.
   * @return The matching author or {@link Optional#empty()} if there is none.
   */
  public Optional<Author> getById(final Integer id) {
    return repository.getById(id).map(record -> new Author(record.id(), record.name()));
  }

  /**
   * Creates a new author with the given data.
   *
   * @param data The data that should be used to create the author.
   */
  public void create(final CreateAuthorRequest data) {
    repository.create(new AuthorRecord(data.id(), data.name()));
  }
}
