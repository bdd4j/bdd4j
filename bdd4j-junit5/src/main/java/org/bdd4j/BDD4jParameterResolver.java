package org.bdd4j;

import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * A {@link ParameterResolver} that can be used in BDD4j tests to inject step instances.
 */
public class BDD4jParameterResolver implements ParameterResolver
{
    private static Object getInstanceOfScenarioBuildersParameterizedType(Constructor<?> ctor, Type parameterizedType)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException
    {
        var type = GenericTypeReflector.getExactParameterTypes(ctor, parameterizedType)[0];

        return Thread.currentThread()
                     .getContextClassLoader()
                     .loadClass(type.getTypeName())
                     .getConstructors()[0].newInstance();
    }

    private static Constructor<?> getScenarioBuilderConstructor()
        throws NoSuchMethodException
    {
        return ScenarioBuilder.class
            .getDeclaredConstructor(BDD4jSteps.class);
    }

    private static Object instantiateScenarioBuilder(Parameter scenarioBuilderParameter, Class<?> type)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
        ClassNotFoundException
    {
        Constructor<?> ctor = getScenarioBuilderConstructor();
        Object parameterizedTypeInstance = getInstanceOfScenarioBuildersParameterizedType(ctor,
                                                                                          scenarioBuilderParameter.getParameterizedType());
        //noinspection JavaReflectionInvocation
        return type.getConstructor(BDD4jSteps.class)
                   .newInstance(parameterizedTypeInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext)
        throws ParameterResolutionException
    {
        try
        {
            var parameter = parameterContext.getParameter();
            Class<?> type = parameter.getType();

            if (type.equals(ScenarioBuilder.class))
                return instantiateScenarioBuilder(parameter, type);

            return type.getConstructors()[0].newInstance();

        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException |
                       NoSuchMethodException | ClassNotFoundException e)
        {
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
        throws ParameterResolutionException
    {
        return BDD4jSteps.class.isAssignableFrom(parameterContext.getParameter()
                                                                 .getType())
            || ScenarioBuilder.class.isAssignableFrom(parameterContext.getParameter()
                                                                      .getType());
    }
}
