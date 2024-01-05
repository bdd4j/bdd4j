package org.bdd4j.internal;

import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.DataRow;
import org.bdd4j.api.ScenarioBuilder;
import org.bdd4j.api.ScenarioOutlineSpec;
import org.bdd4j.api.ScenarioSpec;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

/**
 * An executor that executes the BDD4j tests.
 */
public class BDD4jTestExecutor {

  /**
   * Executes the given request.
   *
   * @param request    The request.
   * @param descriptor The descriptor.
   */
  public void execute(final ExecutionRequest request,
                      final TestDescriptor descriptor) {
    request.getEngineExecutionListener().executionStarted(descriptor);
    if (descriptor instanceof EngineDescriptor) {
      executeContainer(request, descriptor);
    } else if (descriptor instanceof ClassTestDescriptor) {
      executeContainer(request, descriptor);
    } else if (descriptor instanceof BDD4jFeatureDescriptor) {
      executeContainer(request, descriptor);
    } else if (descriptor instanceof BDD4jScenarioDescriptor scenario) {
      executeScenario(scenario);
    } else if (descriptor instanceof BDD4jScenarioOutlineDescriptor scenario) {
      executeScenarioOutline(scenario);
    }

    request.getEngineExecutionListener()
        .executionFinished(descriptor, TestExecutionResult.successful());
  }

  /**
   * Executes the scenario outline definition.
   *
   * @param scenario The scenario.
   */
  private void executeScenarioOutline(final BDD4jScenarioOutlineDescriptor scenario) {
    try {
      final Object testInstance = ReflectionSupport.newInstance(scenario.getTestClass());

      final var type =
          GenericTypeReflector.getExactReturnType(scenario.getMethod(),
              scenario.getTestClass());

      final ScenarioOutlineSpec<?, ?> spec =
          (ScenarioOutlineSpec<?, ?>) scenario.getMethod().invoke(testInstance);

      invokeScenarioOutlineSpec(type, spec, scenario.getRow(), scenario.getTestClass());
    } catch (final IllegalAccessException | InvocationTargetException | ClassNotFoundException |
                   InstantiationException e) {
      throw new RuntimeException(e);
    }
  }

  private <S extends BDD4jSteps<T>, T> void invokeScenarioOutlineSpec(
      final Type type,
      final ScenarioOutlineSpec<S, T> spec,
      final DataRow row,
      final Class<?> testClass)
      throws ClassNotFoundException, InvocationTargetException, InstantiationException,
      IllegalAccessException {
    final String[] tokens = type.toString().split("[<,>]");

    final S steps =
        (S) this.getClass().getClassLoader().loadClass(tokens[1])
            .getConstructors()[0].newInstance();

    final ScenarioBuilder<S, T> builder = new ScenarioBuilder<>(steps);

    spec.apply(builder, steps, row)
        .withTestReporter(new LoggingTestReporter(testClass))
        .run();
  }

  private void executeScenario(final BDD4jScenarioDescriptor scenario) {
    try {
      final Object testInstance = ReflectionSupport.newInstance(scenario.getTestClass());

      final var type =
          GenericTypeReflector.getExactReturnType(scenario.getMethod(),
              scenario.getTestClass());

      if (!List.of(ScenarioSpec.class, ScenarioOutlineSpec.class)
          .contains(scenario.getMethod().getReturnType())) {
        throw new AssertionError("The test method " + scenario.getMethod() + " in class " +
            scenario.getTestClass() +
            " does not return a ScenarioSpec or ScenarioOutlineSpec");
      }

      final ScenarioSpec<?, ?> spec =
          (ScenarioSpec<?, ?>) scenario.getMethod().invoke(testInstance);

      invokeScenarioSpec(type, spec, scenario.getTestClass());
    } catch (final IllegalAccessException | InvocationTargetException | ClassNotFoundException |
                   InstantiationException e) {
      throw new RuntimeException(e);
    }
  }

  private <S extends BDD4jSteps<T>, T> void invokeScenarioSpec(
      final Type type,
      final ScenarioSpec<S, T> spec,
      final Class<?> testClass)
      throws InstantiationException, IllegalAccessException, InvocationTargetException,
      ClassNotFoundException {
    final String[] tokens = type.toString().split("[<,>]");

    final S steps =
        (S) this.getClass().getClassLoader().loadClass(tokens[1])
            .getConstructors()[0].newInstance();

    final ScenarioBuilder<S, T> builder = new ScenarioBuilder<>(steps);

    spec.apply(builder, steps)
        .withTestReporter(new LoggingTestReporter(testClass))
        .run();
  }

  private void executeContainer(final ExecutionRequest request,
                                final TestDescriptor descriptor) {
    descriptor.getChildren().forEach(child -> execute(request, child));
  }
}
