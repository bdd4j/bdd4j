package org.bdd4j.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.platform.commons.annotation.Testable;

/**
 * An annotation that can be used to describe a scenario.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Testable
@BDD4jTest
public @interface Scenario {
  /**
   * The name of the scenario.
   *
   * @return The name.
   */
  String value();
}
