package org.bdd4j.example.postgresql;

/**
 * An exception that indicates that some interaction with the repository failed.
 */
public class RepositoryException extends Error {

  /**
   * Creates a new instance.
   *
   * @param message The message that describes the exception.
   * @param cause   The actual cause of the exception.
   */
  public RepositoryException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
