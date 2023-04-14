package org.bdd4j.example.postgresql.service;

import org.bdd4j.example.postgresql.repository.BooksRecord;
import org.bdd4j.example.postgresql.repository.BooksRepository;

/**
 * A service that can be used to interact with books.
 */
public final class BookService {

  private final BooksRepository repository;
  private final AuthorService authorService;

  /**
   * Creates a new instance.
   *
   * @param repository    The repository that should be used by this instance.
   * @param authorService The author service that should be used by this instance.
   */
  public BookService(final BooksRepository repository,
                     final AuthorService authorService) {

    this.repository = repository;
    this.authorService = authorService;
  }

  /**
   * Lists a new book with the given data.
   *
   * @param data The data that should be used to list the book.
   * @return The listed book.
   */
  public Book listBook(final ListBookRequest data) {
    final var author = authorService.getById(data.authorId());

    if (author.isEmpty()) {
      throw new ServiceException(
          String.format("The author with the ID %s does not exist", data.authorId()));
    }

    repository.create(new BooksRecord(data.id(), data.name(), data.authorId()));

    return repository.findById(data.id())
        .map(record -> new Book(record.id(), record.name(), author.get()))
        .orElseThrow(() -> new ServiceException("Failed to read book after listing it"));
  }
}
