package org.bdd4j;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that can be used to describe the user story.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(BDD4jInvocationInterceptor.class)
public @interface UserStory
{
    /**
     * The user story.
     *
     * @return The user story.
     */
    String value();
}
