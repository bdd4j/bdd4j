package org.bdd4j;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.InvocationTargetException;

/**
 * A {@link ParameterResolver} that can be used in BDD4j tests to inject step instances.
 */
public class BDD4jParameterResolver implements ParameterResolver
{
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
            return parameterContext.getParameter()
                                   .getType()
                                   .getConstructors()[0].newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e)
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
