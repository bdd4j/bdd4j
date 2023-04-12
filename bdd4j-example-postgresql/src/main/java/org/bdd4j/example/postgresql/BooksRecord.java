package org.bdd4j.example.postgresql;

/**
 * A record that can be used to represent a book.
 *
 * @param id     The ID of the book.
 * @param name   The name of the book.
 * @param author The ID of the books author.
 */
public record BooksRecord(Integer id, String name, Integer author) {
}
