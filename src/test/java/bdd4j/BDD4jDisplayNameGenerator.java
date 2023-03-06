package bdd4j;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

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
    return "Feature: " + testClass.getAnnotation(Feature.class).value();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generateDisplayNameForNestedClass(final Class<?> nestedClass)
  {
    return "Feature: " + nestedClass.getAnnotation(Feature.class).value();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod)
  {
    return "Scenario: " + testMethod.getAnnotation(Scenario.class).value();
  }


}
