package org.bdd4j.internal;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

/**
 * A descriptor for a bdd4j feature.
 */
public class BDD4jFeatureDescriptor extends AbstractTestDescriptor {

  private final Class<?> testClass;

  /**
   * Creates a new instance.
   *
   * @param uniqueId    The unique ID.
   * @param displayName The display name.
   * @param testClass   The test class.
   */
  public BDD4jFeatureDescriptor(final UniqueId uniqueId,
                                final String displayName,
                                final Class<?> testClass) {
    super(uniqueId, displayName);
    this.testClass = testClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType() {
    return Type.CONTAINER;
  }

  /**
   * The test class that is represented by the descriptor.
   *
   * @return The test class.
   */
  public Class<?> getTestClass() {
    return testClass;
  }
}
