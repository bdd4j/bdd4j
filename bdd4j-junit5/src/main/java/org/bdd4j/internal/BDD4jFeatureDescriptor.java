package org.bdd4j.internal;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class BDD4jFeatureDescriptor extends AbstractTestDescriptor {

  private final Class<?> testClass;

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

  public Class<?> getTestClass() {
    return testClass;
  }
}
