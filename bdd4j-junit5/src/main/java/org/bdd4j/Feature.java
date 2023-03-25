package org.bdd4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that can be used to describe a specific feature.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Feature {
  /**
   * The name of the feature.
   *
   * @return The name of the feature.
   */
  String title();

  /**
   * A unique identifier for the feature.
   *
   * @return The identifier.
   */
  String identifier();
}
