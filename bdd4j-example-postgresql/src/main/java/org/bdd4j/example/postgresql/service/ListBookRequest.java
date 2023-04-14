package org.bdd4j.example.postgresql.service;

/**
 * A request that can be used to list a new book.
 *
 * @param id       The ID of the book.
 * @param name     The name of the book.
 * @param authorId The ID of the author.
 */
public record ListBookRequest(int id, String name, int authorId) {
}
