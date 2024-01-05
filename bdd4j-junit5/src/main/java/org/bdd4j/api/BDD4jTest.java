package org.bdd4j.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bdd4j.internal.BDD4jDisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;

/**
 * A meta annotation that can be used to mark BDD4j tests.
 */
@DisplayNameGeneration(BDD4jDisplayNameGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface BDD4jTest {
}
