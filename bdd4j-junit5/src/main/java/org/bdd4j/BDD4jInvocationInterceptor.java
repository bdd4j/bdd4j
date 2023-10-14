package org.bdd4j;

import static org.bdd4j.Util.ifNonNullApply;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * BDD4jInvocationInterceptor is an Extensions that intercepts calls to test code.
 * It gathers information about the test for reporting purposes and sets up a scenario runner to
 * execute the steps involved in a bdd4j test.
 */
public final class BDD4jInvocationInterceptor implements InvocationInterceptor {

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T interceptTestClassConstructor(final Invocation<T> invocation,
                                             final ReflectiveInvocationContext<Constructor<T>> invocationContext,
                                             final ExtensionContext extensionContext)
      throws Throwable {
    extensionContext.publishReportEntry(generateInfrastructureReportEntry().asMap());

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
    extensionContext.publishReportEntry(
        BDD4jReportEntry.builder()
            .type(TestEventType.FEATURE_METADATA_REPORTED)
            .with("feature",
                readValueOfFeatureAnnotationIfPresent(invocationContext.getTargetClass()))
            .with("story",
                readValueOfUserStoryAnnotationIfPresent(invocationContext.getTargetClass()))
            .build()
            .asMap());

    extensionContext.publishReportEntry(
        BDD4jReportEntry.builder()
            .type(TestEventType.SCENARIO_METADATA_REPORTED)
            .with("scenario", invocationContext.getExecutable()
                .getAnnotation(Scenario.class)
                .value())
            .build()
            .asMap());

    invocation.proceed();

    invocationContext.getArguments()
        .stream()
        .filter(o -> o instanceof ScenarioBuilder)
        .map(o -> (ScenarioBuilder<?, ?>) o)
        .findFirst()
        .orElseThrow()
        .withTestReporter(extensionContext::publishReportEntry)
        .build()
        .run();
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

  /**
   * Generates the infrastructure report entry.
   *
   * @return The entry.
   */
  private BDD4jReportEntry generateInfrastructureReportEntry() {
    return BDD4jReportEntry.builder().type(TestEventType.INFRASTRUCTURE_REPORTED)
        .with("hostname", InfrastructureHelper.determineHostname())
        .with("username", InfrastructureHelper.determineUsername())
        .with("operating_system", InfrastructureHelper.determineOperatingSystem())
        .with("cores", String.valueOf(InfrastructureHelper.determineNumberOfCores()))
        .with("java_version", InfrastructureHelper.determineJavaVersion())
        .with("file_encoding", InfrastructureHelper.determineFileEncoding())
        .with("heap_size", String.valueOf(InfrastructureHelper.determineHeapSize())).build();
  }
}
