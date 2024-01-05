package org.bdd4j.internal;

import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.ScenarioBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * A {@link ParameterResolver} that can be used in BDD4j tests to inject step instances.
 */
public class BDD4jParameterResolver implements ParameterResolver {
  /**
   * Retrieves an instance of the scenario builders parameterized type.
   *
   * @param ctor              The constructor.
   * @param parameterizedType The parameterized type.
   * @return An instance of the scenario builders parameterized type.
   * @throws InstantiationException    Might be thrown in case that the scenario builder could not be instantiated.
   * @throws IllegalAccessException    Might be thrown in case that the type could not be accessed.
   * @throws InvocationTargetException Might be thrown in case that the underlying constructor throws an exception.
   * @throws ClassNotFoundException    Might be thrown in case that the class could not be found.
   */
  private static Object getInstanceOfScenarioBuildersParameterizedType(final Constructor<?> ctor,
                                                                       final Type parameterizedType)
      throws InstantiationException, IllegalAccessException, InvocationTargetException,
      ClassNotFoundException {
    final var type = GenericTypeReflector.getExactParameterTypes(ctor, parameterizedType)[0];

    return Thread.currentThread()
        .getContextClassLoader()
        .loadClass(type.getTypeName())
        .getConstructors()[0].newInstance();
  }

  /**
   * Retrieves the constructor of the scenario builder.
   *
   * @return The constructor.
   * @throws NoSuchMethodException Might be thrown in case that the constructor could not be retrieved.
   */
  private static Constructor<?> getScenarioBuilderConstructor()
      throws NoSuchMethodException {
    return ScenarioBuilder.class.getDeclaredConstructor(BDD4jSteps.class);
  }

  /**
   * Instantiates the scenario builder.
   *
   * @param scenarioBuilderParameter The parameter of the scenario builder.
   * @param type                     The type of the parameter.
   * @return The instance of the scenario builder.
   * @throws NoSuchMethodException     Might be thrown in case that the method could not be found.
   * @throws InstantiationException    Might be thrown in case that the scenario builder could not be instantiated.
   * @throws IllegalAccessException    Might be thrown in case that the type could not be accessed.
   * @throws InvocationTargetException Might be thrown in case that the underlying constructor throws an exception.
   * @throws ClassNotFoundException    Might be thrown in case that the class could not be found.
   */
  public static Object instantiateScenarioBuilder(final Parameter scenarioBuilderParameter,
                                                   final Class<?> type)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException,
      InvocationTargetException,
      ClassNotFoundException {
    final Constructor<?> ctor = getScenarioBuilderConstructor();
    final Object parameterizedTypeInstance = getInstanceOfScenarioBuildersParameterizedType(ctor,
        scenarioBuilderParameter.getParameterizedType());
    //noinspection JavaReflectionInvocation
    return type.getConstructor(BDD4jSteps.class).newInstance(parameterizedTypeInstance);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object resolveParameter(final ParameterContext parameterContext,
                                 final ExtensionContext extensionContext)
      throws ParameterResolutionException {
    try {
      final var parameter = parameterContext.getParameter();
      final Class<?> type = parameter.getType();

      if (type.equals(ScenarioBuilder.class)) {
        return instantiateScenarioBuilder(parameter, type);
      }

      return type.getConstructors()[0].newInstance();

    } catch (final InstantiationException | IllegalAccessException | InvocationTargetException |
                   NoSuchMethodException | ClassNotFoundException e) {
      throw new ParameterResolutionException("Failed to create instance of type " +
          parameterContext.getParameter()
              .getType()
              .getName(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supportsParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return BDD4jSteps.class.isAssignableFrom(parameterContext.getParameter().getType())
        || ScenarioBuilder.class.isAssignableFrom(parameterContext.getParameter().getType());
  }
}
