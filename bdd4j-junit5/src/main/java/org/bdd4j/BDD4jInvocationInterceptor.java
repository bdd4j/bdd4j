package org.bdd4j;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static java.util.Objects.nonNull;

public class BDD4jInvocationInterceptor implements InvocationInterceptor
{

    private String feature;
    private String story;

    @Override
    public <T> T interceptTestClassConstructor(Invocation<T> invocation,
                                               ReflectiveInvocationContext<Constructor<T>> invocationContext,
                                               ExtensionContext extensionContext) throws Throwable
    {
        //it would be much nicer if the annotation value would be reported directly via
        //extensionContext.publishReportEntry(), but for some reasons, that report entry "gets lost"
        //This could be related to https://github.com/junit-team/junit5/issues/2277
        story = readValueOfUserStoryAnnotationIfPresent(invocationContext.getTargetClass());
        feature = readValueOfFeatureAnnotationIfPresent(invocationContext.getTargetClass());

        return InvocationInterceptor.super.interceptTestClassConstructor(invocation, invocationContext,
                                                                         extensionContext);
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable
    {
        if (nonNull(feature))
        {
            extensionContext.publishReportEntry("Feature", feature);
            feature = null;
        }
        if (nonNull(story))
        {
            extensionContext.publishReportEntry("UserStory", story);
            story = null;
        }

        invocation.proceed();
        var scenarioBuilder = invocationContext.getArguments()
                                               .stream()
                                               .filter(o -> o instanceof ScenarioBuilder)
                                               .map(o -> (ScenarioBuilder) o)
                                               .findFirst()
                                               .orElseThrow();
        var stepsWrapper = invocationContext.getArguments()
                                            .stream()
                                            .filter(o -> o instanceof BDD4jSteps<?>)
                                            .map(o -> (BDD4jSteps<?>) o)
                                            .findFirst()
                                            .orElseThrow();

        //noinspection unchecked
        BDD4jRunner.scenario(stepsWrapper, extensionContext::publishReportEntry, scenarioBuilder.steps()
                                                                                                .toArray(new Step[0]));

    }

    private String readValueOfFeatureAnnotationIfPresent(Class<?> targetClass)
    {
        if (targetClass.isAnnotationPresent(Feature.class))
            return targetClass.getAnnotation(Feature.class)
                              .value();
        return null;
    }

    private String readValueOfUserStoryAnnotationIfPresent(Class<?> targetClass)
    {
        if (targetClass.isAnnotationPresent(UserStory.class))
            return targetClass.getAnnotation(UserStory.class)
                              .value();
        return null;
    }
}
