package org.bdd4j.example.postgresql.service;

/**
 * A request that can be used to create a new author.
 *
 * @param id   The ID of the author.
 * @param name The name of the author.
 */
public record CreateAuthorRequest(int id, String name) {
}
