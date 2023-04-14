package org.bdd4j.example.postgresql.service;

/**
 * A domain object that can be used to represent a book.
 *
 * @param id     The ID of the book.
 * @param name   The name of the book.
 * @param author The author of the book.
 */
public record Book(Integer id, String name, Author author) {
}
