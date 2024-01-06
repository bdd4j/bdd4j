package org.bdd4j.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.Given;
import org.bdd4j.api.Then;
import org.bdd4j.api.When;

public final class FeatureReflectionUtil {

  private static final Collection<Class<?>> STEP_RETURN_TYPES =
      List.of(Given.class, When.class, Then.class);

  /**
   * Private constructor to prevent instantiation.
   */
  private FeatureReflectionUtil() {
  }

  /**
   * Creates an instance of the specified steps class.
   *
   * @param clazz The steps class to instantiate.
   * @return An instance of the specified steps class.
   */
  public static BDD4jSteps<Object> instantiateSteps(final Class<? extends BDD4jSteps<?>> clazz) {
    try {
      return (BDD4jSteps<Object>) clazz.getConstructors()[0].newInstance();
    } catch (final InstantiationException e) {
      throw new FeatureParserException("Failed to instantiate class " + clazz, e);
    } catch (final IllegalAccessException e) {
      throw new FeatureParserException("The constructor of " + clazz + " is inaccessible", e);
    } catch (final InvocationTargetException e) {
      throw new FeatureParserException("The constructor of " + clazz + " threw an exception", e);
    }
  }

  /**
   * Finds all step builder methods in the given steps class.
   *
   * @param clazz The steps class.
   * @return A collection of step builder methods.
   */
  public static Collection<Method> findStepBuilderMethods(
      final Class<? extends BDD4jSteps<?>> clazz) {
    return Arrays.stream(clazz.getMethods())
        .filter(method -> STEP_RETURN_TYPES.contains(method.getReturnType()))
        .toList();
  }
}
