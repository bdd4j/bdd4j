package org.bdd4j.internal;

import java.lang.reflect.Method;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

/**
 * A test descriptor for bdd4j scenarios.
 */
public class BDD4jScenarioDescriptor extends AbstractTestDescriptor {

  private final Method method;
  private final Class<?> testClass;

  /**
   * Creates a new instance.
   *
   * @param uniqueId    The unique ID.
   * @param displayName The display name.
   * @param method      The method to invoke.
   * @param testClass   The test class that the scenario belongs to.
   */
  public BDD4jScenarioDescriptor(final UniqueId uniqueId,
                                 final String displayName,
                                 final Method method,
                                 final Class<?> testClass) {
    super(uniqueId, displayName);
    this.method = method;
    this.testClass = testClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType() {
    return Type.TEST;
  }

  /**
   * The actual test method.
   *
   * @return The test method.
   */
  public Method getMethod() {
    return method;
  }

  /**
   * The test class.
   *
   * @return The test class.
   */
  public Class<?> getTestClass() {
    return testClass;
  }
}
