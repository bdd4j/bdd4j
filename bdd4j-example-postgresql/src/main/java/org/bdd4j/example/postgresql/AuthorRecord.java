package org.bdd4j.example.postgresql;

/**
 * A record that can be used to represent an author.
 *
 * @param id   The ID of the author.
 * @param name The name of the author.
 */
public record AuthorRecord(Integer id, String name) {
}
