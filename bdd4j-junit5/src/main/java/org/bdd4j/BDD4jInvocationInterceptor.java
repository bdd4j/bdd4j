package org.bdd4j;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;

public class BDD4jInvocationInterceptor implements InvocationInterceptor
{
    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable
    {
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
        BDD4jRunner.scenario(stepsWrapper, extensionContext::publishReportEntry, scenarioBuilder.steps()
                                                                                                .toArray(new Step[0]));

//        InvocationInterceptor.super.interceptTestMethod(invocation, invocationContext, extensionContext);
    }
}
