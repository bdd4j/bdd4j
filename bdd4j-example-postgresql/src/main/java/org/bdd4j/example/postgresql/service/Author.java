package org.bdd4j.example.postgresql.service;

/**
 * A domain object that can be used to represent the author.
 *
 * @param id   The ID of the author.
 * @param name The name.
 */
public record Author(Integer id, String name) {
}
