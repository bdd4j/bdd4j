package org.bdd4j.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bdd4j.internal.BDD4jDisplayNameGenerator;
import org.bdd4j.internal.BDD4jParameterResolver;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * A meta annotation that can be used to mark BDD4j tests.
 */
@DisplayNameGeneration(BDD4jDisplayNameGenerator.class)
@ExtendWith(BDD4jParameterResolver.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface BDD4jTest {
}