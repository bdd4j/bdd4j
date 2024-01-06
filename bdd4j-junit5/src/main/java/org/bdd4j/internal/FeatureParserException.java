package org.bdd4j.internal;

/**
 * An exception that indicates that parsing a feature failed.
 */
public class FeatureParserException extends Error {

  /**
   * Creates a new instance.
   *
   * @param message The message that describes the exception.
   */
  public FeatureParserException(final String message) {
    super(message);
  }

  /**
   * Creates a new instance.
   *
   * @param message The message that describes the exception.
   * @param cause   The actual cause of the exception.
   */
  public FeatureParserException(final String message, final Exception cause) {
    super(message, cause);
  }
}
