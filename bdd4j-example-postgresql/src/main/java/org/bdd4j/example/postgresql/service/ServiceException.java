package org.bdd4j.example.postgresql.service;

/**
 * An exception that can be used to indicate that interacting with a service failed.
 */
public class ServiceException extends Error {

  /**
   * Creates a new instance.
   *
   * @param message The message that describes the exception.
   */
  public ServiceException(final String message) {
    super(message);
  }

  /**
   * Creates a new instance.
   *
   * @param message The message that describes the exception.
   * @param cause   The actual cause of the exception.
   */
  public ServiceException(final String message,
                          final Throwable cause) {
    super(message, cause);
  }
}
