package org.bdd4j.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.platform.commons.annotation.Testable;


/**
 * An annotation that can be used to create parameterized scenarios.
 * <p>
 * Due to a conflict between parameter resolvers the injected steps have to be placed as the last
 * parameter for the annotated method. See
 * <a href="https://github.com/bdd4j/bdd4j/issues/2">this issue</a> for more details.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Testable
@BDD4jTest
public @interface ScenarioOutline {
  /**
   * The description of the scenario.
   *
   * @return The description.
   */
  String description();

  /**
   * The data of the scenario outline.
   *
   * @return The data.
   */
  String data();
}
