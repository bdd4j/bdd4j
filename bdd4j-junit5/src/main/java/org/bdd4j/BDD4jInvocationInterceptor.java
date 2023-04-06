package org.bdd4j;

import static java.util.Objects.nonNull;
import static org.bdd4j.Util.ifNonNullApply;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * BDD4jInvocationInterceptor is an Extensions that intercepts calls to test code.
 * It gathers information about the test for reporting purposes and sets up a scenario runner to execute the steps involved in a bdd4j test.
 */
public final class BDD4jInvocationInterceptor implements InvocationInterceptor {

  private String feature;
  private String story;

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T interceptTestClassConstructor(final Invocation<T> invocation,
                                             final ReflectiveInvocationContext<Constructor<T>> invocationContext,
                                             final ExtensionContext extensionContext)
      throws Throwable {
    //it would be much nicer if the annotation value would be reported directly via
    //extensionContext.publishReportEntry(), but for some reasons, that report entry "gets lost"
    //This could be related to https://github.com/junit-team/junit5/issues/2277
    story = readValueOfUserStoryAnnotationIfPresent(invocationContext.getTargetClass());
    feature = readValueOfFeatureAnnotationIfPresent(invocationContext.getTargetClass());

    return InvocationInterceptor.super.interceptTestClassConstructor(invocation, invocationContext,
        extensionContext);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void interceptTestMethod(final Invocation<Void> invocation,
                                  final ReflectiveInvocationContext<Method> invocationContext,
                                  final ExtensionContext extensionContext) throws Throwable {
    if (nonNull(feature)) {
      extensionContext.publishReportEntry("Feature", feature);
      feature = null;
    }
    if (nonNull(story)) {
      extensionContext.publishReportEntry("UserStory", story);
      story = null;
    }

    invocation.proceed();
    var scenarioBuilder = invocationContext.getArguments()
        .stream()
        .filter(o -> o instanceof ScenarioBuilder)
        .map(o -> (ScenarioBuilder<?>) o)
        .findFirst()
        .orElseThrow();

    var stepsWrapper = scenarioBuilder.availableSteps();

    //noinspection unchecked
    BDD4jRunner.scenario(stepsWrapper, extensionContext::publishReportEntry,
        scenarioBuilder.registeredSteps()
            .toArray(new Step[0]));
  }

  /**
   * Reads the value of the feature annotation if it is present on the given class.
   *
   * @param targetClass The class.
   * @return The value of the feature annotation.
   */
  private String readValueOfFeatureAnnotationIfPresent(final Class<?> targetClass) {
    return ifNonNullApply(targetClass.getAnnotation(Feature.class), Feature::value);

  }

  /**
   * Reads the value of the user story annotation if it is present on the given class.
   *
   * @param targetClass The class.
   * @return The value of the user story annotation.
   */
  private String readValueOfUserStoryAnnotationIfPresent(final Class<?> targetClass) {
    return ifNonNullApply(targetClass.getAnnotation(UserStory.class), UserStory::value);
  }
}
