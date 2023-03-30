package org.bdd4j;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * A {@link org.junit.jupiter.api.DisplayNameGeneration} for BDD4j test cases.
 */
public class BDD4jDisplayNameGenerator implements DisplayNameGenerator
{
    /**
     * {@inheritDoc}
     */
    @Override
    public String generateDisplayNameForClass(final Class<?> testClass)
    {
        return "Feature: " +
                Optional.ofNullable(testClass.getAnnotation(Feature.class))
                        .map(Feature::value)
                        .orElse(testClass.getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass)
    {
        return "Feature: " +
                Optional.ofNullable(nestedClass.getAnnotation(Feature.class))
                        .map(Feature::value)
                        .orElse(nestedClass.getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod)
    {
        return "Scenario: " +
                Optional.ofNullable(testMethod.getAnnotation(Scenario.class))
                        .map(Scenario::value)
                        .orElse(testMethod.getName());
    }
}
